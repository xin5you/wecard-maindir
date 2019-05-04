package com.cn.thinkx.oms.module.enterpriseorder.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.constants.Constants;
import com.cn.thinkx.oms.module.enterpriseorder.mapper.BatchOrderListMapper;
import com.cn.thinkx.oms.module.enterpriseorder.mapper.BatchOrderMapper;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchOrder;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchOrderList;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchOrderListService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.NumberUtils;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("batchOrderListService")
public class BatchOrderListServiceImpl implements BatchOrderListService {
	
	@Autowired
	@Qualifier("batchOrderListMapper")
	private BatchOrderListMapper batchOrderListMapper;
	
	@Autowired
	@Qualifier("batchOrderMapper")
	private BatchOrderMapper batchOrderMapper;

	@Override
	public List<BatchOrderList> getBatchOrderListList(String orderId) {
		return batchOrderListMapper.getBatchOrderListList(orderId);
	}

	@Override
	public int addBatchOrderList(List<BatchOrderList> list) {
		return batchOrderListMapper.addBatchOrderList(list);
	}

	@Override
	public PageInfo<BatchOrderList> getBatchOrderListPage(int startNum, int pageSize, String orderId) {
		PageHelper.startPage(startNum, pageSize);
		List<BatchOrderList> list = getBatchOrderListList(orderId);
		for (BatchOrderList batchOrderList : list) {
			batchOrderList.setOrderStat(BaseConstants.OMSOrderListStat.findOrderListStat(batchOrderList.getOrderStat()));
			if(batchOrderList.getAmount()!=null){
				batchOrderList.setAmount(""+NumberUtils.formatMoney(batchOrderList.getAmount()));
			}
		}
		PageInfo<BatchOrderList> page = new PageInfo<BatchOrderList>(list);
		return page;
	}

	@Override
	public int addOrderList(BatchOrderList orderList) {
		BatchOrder bo = new BatchOrder();
		bo.setOrderId(orderList.getOrderId());
		bo.setUpdateTime(new Date());
		batchOrderMapper.updateBatchOrder(bo);
		return batchOrderListMapper.addOrderList(orderList);
	}

	@Override
	public int deleteBatchOrderList(HttpServletRequest req) {
		String orderListId = StringUtils.nullToString(req.getParameter("orderListId"));
		HttpSession session=req.getSession();
		User user=(User)session.getAttribute(Constants.SESSION_USER);
		BatchOrderList bol = getBatchOrderListByOrderListId(orderListId);
		BatchOrder bo = batchOrderMapper.getBatchOrderById(bol.getOrderId());
		bo.setUpdateUser(user.getId().toString());
		bo.setUpdateTime(new Date());
		batchOrderMapper.updateBatchOrder(bo);
		return batchOrderListMapper.deleteBatchOrderList(orderListId);
	}

	@Override
	public int updateBatchOrderList(BatchOrderList orderList) {
		return batchOrderListMapper.updateBatchOrderList(orderList);
	}

	@Override
	public List<BatchOrderList> getBatchOrderListFailList(String orderId) {
		return batchOrderListMapper.getBatchOrderListFailList(orderId);
	}

	@Override
	public BatchOrderList getBatchOrderListByOrderIdAndPhoneNo(BatchOrderList orderList) {
		return batchOrderListMapper.getBatchOrderListByOrderIdAndPhoneNo(orderList);
	}

	@Override
	public BatchOrderList getBatchOrderListByOrderListId(String orderListId) {
		return batchOrderListMapper.getBatchOrderListByOrderListId(orderListId);
	}

}
