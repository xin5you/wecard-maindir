package com.cn.thinkx.oms.module.merchant.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.merchant.mapper.TransLogMapper;
import com.cn.thinkx.oms.module.merchant.model.TransInf;
import com.cn.thinkx.oms.module.merchant.model.TransLog;
import com.cn.thinkx.oms.module.merchant.model.TransLogUpload;
import com.cn.thinkx.oms.module.merchant.service.TransLogService;
import com.cn.thinkx.oms.util.NumberUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("transLogService")
public class TransLogSerivceImpl implements TransLogService {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("transLogMapper")
	private TransLogMapper transLogMapper;

	public PageInfo<TransLog> getTransLogPage(int startNum, int pageSize, TransInf entity) {
		List<TransLog> list = null;
		try {
//			PageHelper.startPage(startNum, pageSize);
//			list = this.getTransLogList(entity);
			if (StringUtil.isNullOrEmpty(entity.getQueryType()) || "cur".equals(entity.getQueryType())) {
				String curLogNum = transLogMapper.getCurLogNum();
				entity.setTableNum(curLogNum);
			}
			
			PageHelper.startPage(startNum, pageSize);
			if ("his".equals(entity.getQueryType()))
				list = transLogMapper.getTransLogHisByMchntCode(entity);
			else {
				list = transLogMapper.getTransLogCurByMchntCode(entity);
			}
			if (list != null && list.size() > 0) {
				for (TransLog obj : list) {
					obj.setSettleDate(DateUtil.getFormatStringFormString(obj.getSettleDate(), "yyyy-MM-dd"));
					if(obj.getTransAmt()!=null && !"".equals(obj.getTransAmt())){
						obj.setTransAmt("" + NumberUtils.formatMoney(obj.getTransAmt()));
					}
					if(!StringUtil.isEmpty(obj.getRespCode())){
						obj.setRespCode(BaseConstants.ITFRespCode.findByCode(obj.getRespCode()).getValue());
					}
					if(!StringUtil.isEmpty(obj.getTransId())){
						obj.setTransId(BaseConstants.TransCode.findByCode(obj.getTransId()).getValue());
					}
				}
			}
		}  catch (Exception e) {
			logger.error("查询交易明细列表出错：", e);
		}
		PageInfo<TransLog> page = new PageInfo<TransLog>(list);
		
		return page;
	}

	@Override
	public List<TransLogUpload> getTransLogList(TransInf entity) {
		List<TransLogUpload> list = null;
		try {
			if (StringUtil.isNullOrEmpty(entity.getQueryType()) || "cur".equals(entity.getQueryType())) {
				String curLogNum = transLogMapper.getCurLogNum();
				entity.setTableNum(curLogNum);
			}
			
			if ("his".equals(entity.getQueryType()))
				list = transLogMapper.getTransLogUploadHisByMchntCode(entity);
			else {
				list = transLogMapper.getTransLogUploadCurByMchntCode(entity);
			}
			
			if (list != null && list.size() > 0) {
				for (TransLogUpload obj : list) {
					obj.setSettleDate(DateUtil.getFormatStringFormString(obj.getSettleDate(), "yyyy-MM-dd"));
					if(obj.getTransAmt()!=null && !"".equals(obj.getTransAmt())){
						obj.setTransAmt("" + NumberUtils.formatMoney(obj.getTransAmt()));
					}
					if(!StringUtil.isEmpty(obj.getRespCode())){
						obj.setRespCode(BaseConstants.ITFRespCode.findByCode(obj.getRespCode()).getValue());
					}
					if(!StringUtil.isEmpty(obj.getTransId())){
						obj.setTransId(BaseConstants.TransCode.findByCode(obj.getTransId()).getValue());
					}
				}
			}
		}  catch (Exception e) {
			logger.error("查询交易明细列表出错：", e);
		}
		return list;
	}

}
