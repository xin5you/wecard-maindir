package com.cn.thinkx.oms.module.margin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.constants.Constants;
import com.cn.thinkx.oms.module.margin.mapper.MerchantMarginListMapper;
import com.cn.thinkx.oms.module.margin.model.MerchantCashManage;
import com.cn.thinkx.oms.module.margin.model.MerchantMarginList;
import com.cn.thinkx.oms.module.margin.service.MerchantCashManageService;
import com.cn.thinkx.oms.module.margin.service.MerchantMarginService;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("merchantMarginService")
public class MerchantMarginServiceImpl implements MerchantMarginService {

	@Autowired
	@Qualifier("merchantMarginListMapper")
	private MerchantMarginListMapper merchantMarginListMapper;

	@Autowired
	@Qualifier("merchantCashManageService")
	private MerchantCashManageService merchantCashManageService;
	
	@Override
	public MerchantMarginList getMerchantMarginListById(String id) {
		
		return merchantMarginListMapper.getMerchantMarginListById(id);
	}
	
	/**
	 * 追加保证金
	 * @param merchantMarginList
	 * @return
	 */
	public int addMerchantMarginList(MerchantMarginList merchantMarginList){
		MerchantCashManage merchantCashManage=merchantCashManageService.getMerchantCashManageById(merchantMarginList.getChashId());
		merchantMarginList.setRechargeAmt(merchantCashManage.getRechargeAmt());//累计销售金额
		merchantMarginList.setMortgageAmt(merchantCashManage.getMortgageAmt());//押款金额
		merchantMarginList.setGetQuota(merchantCashManage.getGetQuota());//押款获取的额度
		merchantMarginList.setRechargeFaceAmt(merchantCashManage.getRechargeFaceAmt());//累计充值面额
		merchantMarginList.setMerchantId(merchantCashManage.getMchntId());
		merchantMarginList.setMortgageFlg(merchantCashManage.getMortgageFlg());//押款标志
		merchantMarginList.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
		return merchantMarginListMapper.insertMerchantMarginList(merchantMarginList);
	}

	/**
	 * 修改商户保证金
	 */
	public int updateMerchantMarginList(MerchantMarginList merchantMarginList) {
		
		return merchantMarginListMapper.updateMerchantMarginList(merchantMarginList);
	}

	/**
	 * 查找商户保证金
	 */
	public List<MerchantMarginList> getMerchantMarginList(MerchantMarginList entity) {
		
		return merchantMarginListMapper.getMerchantMarginList(entity);
	}

	/**
	 * 保证金查询列表
	 */
	public PageInfo<MerchantMarginList> getMerchantMarginListPage(int startNum, int pageSize,
			MerchantMarginList entity) {

		PageHelper.startPage(startNum, pageSize);
		List<MerchantMarginList> list = getMerchantMarginList(entity);
		PageInfo<MerchantMarginList> page = new PageInfo<MerchantMarginList>(list);
		return page;
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int deleteMerchantMarginListById(String id){
		return merchantMarginListMapper.deleteMerchantMarginList(id);
	}

	/**
	 * 获取审批任务列表
	 * @param startNum
	 * @param pageSize
	 * @param entity
	 * @return
	 */
	public PageInfo<MerchantMarginList> getMerchantMarginApproveList(int startNum, int pageSize,
			MerchantMarginList entity){
		
		PageHelper.startPage(startNum, pageSize);
		List<MerchantMarginList> list = getMerchantMarginApproveList(entity);
		PageInfo<MerchantMarginList> page = new PageInfo<MerchantMarginList>(list);
		return page;
	}
	
	/**
	 * 获取审批任务列表
	 */
	public List<MerchantMarginList> getMerchantMarginApproveList(MerchantMarginList entity) {
		
		return merchantMarginListMapper.getMerchantMarginApproveList(entity);
	}
	
	/**
	 * 商户保证金押款确认
	 * @param id
	 * @return
	 */
	public synchronized int  saveMchntCashConfirm(MerchantMarginList merchantMarginList) throws Exception{
		int oper=0;
		MerchantCashManage m1=merchantCashManageService.getMerchantCashManageById(merchantMarginList.getChashId());
		String mortgageAmt=NumberUtils.addMoney(m1.getMortgageAmt(), merchantMarginList.getAddMortgageAmt());
		String getQuota=NumberUtils.addMoney(m1.getGetQuota(),merchantMarginList.getAddGetQuota());
		m1.setMortgageAmt(mortgageAmt);
		m1.setGetQuota(getQuota);
		
		MerchantCashManage m2=merchantCashManageService.getMerchantCashManageById(merchantMarginList.getChashId());
		if(m2.getLockVersion()==m1.getLockVersion() && Constants.MarginStatEnum.MARGIN_40.getCode().equals(merchantMarginList.getApproveStat())){
			oper=merchantCashManageService.updateMerchantCashManage(m1);
			if(oper>0){
				merchantMarginList.setApproveStat(Constants.MarginStatEnum.MARGIN_50.getCode());
				oper=this.updateMerchantMarginList(merchantMarginList);
			}
		}
		if(oper<1){
			throw new Exception();
		}
		
		return oper;
	}
}
