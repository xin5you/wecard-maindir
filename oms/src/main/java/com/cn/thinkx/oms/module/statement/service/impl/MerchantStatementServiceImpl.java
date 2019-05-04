package com.cn.thinkx.oms.module.statement.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.enterpriseorder.service.impl.BatchOrderServiceImpl;
import com.cn.thinkx.oms.module.statement.mapper.MerchantStatementMapper;
import com.cn.thinkx.oms.module.statement.model.MerchantDetail;
import com.cn.thinkx.oms.module.statement.model.MerchantSummarizing;
import com.cn.thinkx.oms.module.statement.model.ShopDetail;
import com.cn.thinkx.oms.module.statement.service.MerchantStatementService;
import com.cn.thinkx.oms.module.statement.util.ComputeUtil;
import com.cn.thinkx.oms.module.statement.util.Condition;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("merchantStatementService")
public class MerchantStatementServiceImpl implements MerchantStatementService {

	Logger logger = LoggerFactory.getLogger(BatchOrderServiceImpl.class);
	
	@Autowired
	@Qualifier("merchantStatementMapper")
	private MerchantStatementMapper merchantStatementMapper;
	
	@Override
	public List<ShopDetail> getShopDetailList(Condition conditions) {
		List<ShopDetail> list = merchantStatementMapper.getShopDetailList(conditions);
		if(list!=null){
			for (ShopDetail shopDetail : list) {
				shopDetail.setTransAmt(NumberUtils.RMBCentToYuan(shopDetail.getTransAmt()));
			}
		}
		return list;
	}

	@Override
	public PageInfo<ShopDetail> getShopDetailPage(int startNum, int pageSize, Condition condition) {
		PageHelper.startPage(startNum, pageSize);
		List<ShopDetail> list = getShopDetailList(condition);
		PageInfo<ShopDetail> page = new PageInfo<ShopDetail>(list);
		return page;
	}

	@Override
	public List<MerchantDetail> getMerchantDetailList(Condition conditions) {
		List<MerchantDetail> list =merchantStatementMapper.getMerchantDetailList(conditions);
		if(list!=null){
			for (MerchantDetail merchantDetail : list) {
				merchantDetail.setMemberCardConsumeAmt(NumberUtils.RMBCentToYuan(merchantDetail.getMemberCardConsumeAmt()));
				merchantDetail.setSpeedyConsumeAmt(NumberUtils.RMBCentToYuan(merchantDetail.getSpeedyConsumeAmt()));
				merchantDetail.setConsumeAmt(NumberUtils.RMBCentToYuan(merchantDetail.getConsumeAmt()));
			}
		}
		return list;
	}

	@Override
	public PageInfo<MerchantDetail> getMerchantsDetailPage(int startNum, int pageSize, Condition condition) {
		PageHelper.startPage(startNum, pageSize);
		List<MerchantDetail> list = getMerchantDetailList(condition);
		PageInfo<MerchantDetail> page = new PageInfo<MerchantDetail>(list);
		return page;
	}

	@Override
	public MerchantSummarizing getMerchantSummarizing(Condition conditions) {
		MerchantSummarizing ms = merchantStatementMapper.getMerchantSummarizing(conditions);
		if(ms!=null){
			ms.setSettleAmt(NumberUtils.RMBCentToYuan(ComputeUtil.toCompute(ms.getSettleAmt(), ms.getServiceCharge())));
			ms.setMemberCardConsumeAmt(NumberUtils.RMBCentToYuan(ms.getMemberCardConsumeAmt()));
			ms.setSpeedyConsumeAmt(NumberUtils.RMBCentToYuan(ms.getSpeedyConsumeAmt()));
			ms.setXsRechargeUploadAmt(NumberUtils.RMBCentToYuan(ms.getXsRechargeUploadAmt()));
			ms.setXsRechargeTransAmt(NumberUtils.RMBCentToYuan(ms.getXsRechargeTransAmt()));
			ms.setPtRechargeUploadAmt(NumberUtils.RMBCentToYuan(ms.getPtRechargeUploadAmt()));
			ms.setPtRechargeTransAmt(NumberUtils.RMBCentToYuan(ms.getPtRechargeTransAmt()));
			ms.setPtSubsidyAmt(NumberUtils.RMBCentToYuan(ms.getPtSubsidyAmt()));
			ms.setMemberCardBal(NumberUtils.RMBCentToYuan(getCardBal(conditions)));
			ms.setServiceCharge(NumberUtils.RMBCentToYuan(ms.getServiceCharge()));
		}
		return ms;
	}

	@Override
	public String getCardBal(Condition conditions) {
		return merchantStatementMapper.getCardBal(conditions);
	}

	@Override
	public MerchantDetail getMerchantDetailAmount(Condition condition) {
		MerchantDetail md = merchantStatementMapper.getMerchantDetailAmount(condition);
		md.setMemberCardConsumeAmt(NumberUtils.RMBCentToYuan(md.getMemberCardConsumeAmt()));
		md.setSpeedyConsumeAmt(NumberUtils.RMBCentToYuan(md.getSpeedyConsumeAmt()));
		md.setConsumeAmt(NumberUtils.RMBCentToYuan(md.getConsumeAmt()));
		return md;
	}

	@Override
	public ShopDetail getShopDetailAmount(Condition condition) {
		ShopDetail sd = merchantStatementMapper.getShopDetailAmount(condition);
		if(sd!=null){
			if(sd.getTransAmt()!=null){
				sd.setTransAmt(NumberUtils.RMBCentToYuan(sd.getTransAmt()));
			}else{
				sd.setTransAmt(NumberUtils.RMBCentToYuan("0"));
			}
		}
		return sd;
	}

}
