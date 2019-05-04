package com.cn.thinkx.oms.module.statement.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.enterpriseorder.model.BatchOrder;
import com.cn.thinkx.oms.module.enterpriseorder.service.impl.BatchOrderServiceImpl;
import com.cn.thinkx.oms.module.statement.mapper.OperationStatementMapper;
import com.cn.thinkx.oms.module.statement.model.CustomerInfo;
import com.cn.thinkx.oms.module.statement.model.CustomerInfoDetail;
import com.cn.thinkx.oms.module.statement.model.MarketingDetail;
import com.cn.thinkx.oms.module.statement.model.OperationDetail;
import com.cn.thinkx.oms.module.statement.model.OperationSummarizing;
import com.cn.thinkx.oms.module.statement.service.OperationStatementService;
import com.cn.thinkx.oms.module.statement.util.Condition;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("operationStatementService")
public class OperationStatementServiceImpl implements OperationStatementService {
	
	Logger logger = LoggerFactory.getLogger(BatchOrderServiceImpl.class);
	
	@Autowired
	@Qualifier("operationStatementMapper")
	private OperationStatementMapper operationStatementMapper;
	
	@Override
	public List<OperationDetail> getOperationDetailList(Condition condition) {
		List<OperationDetail> list = operationStatementMapper.getOperationDetailList(condition);
		if(list!=null){
			for (OperationDetail operationDetail : list) {
				operationDetail.setMemberCardConsumeAmt(NumberUtils.RMBCentToYuan(operationDetail.getMemberCardConsumeAmt()));
				operationDetail.setWxConsumeAmt(NumberUtils.RMBCentToYuan(operationDetail.getWxConsumeAmt()));
				operationDetail.setJfConsumeAmt(NumberUtils.RMBCentToYuan(operationDetail.getJfConsumeAmt()));
				operationDetail.setWxRechargeUploadAmt(NumberUtils.RMBCentToYuan(operationDetail.getWxRechargeUploadAmt()));
				operationDetail.setWxRechargeTransAmt(NumberUtils.RMBCentToYuan(operationDetail.getWxRechargeTransAmt()));
				operationDetail.setJfRechargeUploadAmt(NumberUtils.RMBCentToYuan(operationDetail.getJfRechargeUploadAmt()));
				operationDetail.setJfRechargeTransAmt(NumberUtils.RMBCentToYuan(operationDetail.getJfRechargeTransAmt()));
				operationDetail.setPtRechargeUploadAmt(NumberUtils.RMBCentToYuan(operationDetail.getPtRechargeUploadAmt()));
				operationDetail.setPtRechargeTransAmt(NumberUtils.RMBCentToYuan(operationDetail.getPtRechargeTransAmt()));
				operationDetail.setMemberCardBal(NumberUtils.RMBCentToYuan(operationDetail.getMemberCardBal()));
			}
		}
		return list;
	}

	@Override
	public PageInfo<OperationDetail> getOperationDetailPage(int startNum, int pageSize, Condition condition) {
		PageHelper.startPage(startNum, pageSize);
		List<OperationDetail> list =getOperationDetailList(condition);
		PageInfo<OperationDetail> page = new PageInfo<OperationDetail>(list);
		return page;
	}

	@Override
	public OperationSummarizing getOperationSummarizing(Condition condition) {
		OperationSummarizing os = operationStatementMapper.getOperationSummarizing(condition);
		if(os!=null){
			os.setMemberCardConsumeAmt(NumberUtils.RMBCentToYuan(os.getMemberCardConsumeAmt()));
			os.setWxConsumeAmt(NumberUtils.RMBCentToYuan(os.getWxConsumeAmt()));
			os.setJfConsumeAmt(NumberUtils.RMBCentToYuan(os.getJfConsumeAmt()));
			os.setWxRechargeUploadAmt(NumberUtils.RMBCentToYuan(os.getWxRechargeUploadAmt()));
			os.setWxRechargeTransAmt(NumberUtils.RMBCentToYuan(os.getWxRechargeTransAmt()));
			os.setJfRechargeUploadAmt(NumberUtils.RMBCentToYuan(os.getJfRechargeUploadAmt()));
			os.setJfRechargeTransAmt(NumberUtils.RMBCentToYuan(os.getJfRechargeTransAmt()));
			os.setPtRechargeUploadAmt(NumberUtils.RMBCentToYuan(os.getPtRechargeUploadAmt()));
			os.setPtRechargeTransAmt(NumberUtils.RMBCentToYuan(os.getPtRechargeTransAmt()));
			os.setPtSubsidyAmt(NumberUtils.RMBCentToYuan(os.getPtSubsidyAmt()));
			os.setMemberCardBal(NumberUtils.RMBCentToYuan(os.getMemberCardBal()));
		}
		return os;
	}

	@Override
	public CustomerInfo getCustomerInfo(Condition condition) {
		return operationStatementMapper.getCustomerInfo(condition);
	}

	@Override
	public CustomerInfoDetail getCustomerInfoDetail(Condition condition) {
		return operationStatementMapper.getCustomerInfoDetail(condition);
	}

	@Override
	public List<MarketingDetail> getMarketingDetailList(Condition condition) {
		List<MarketingDetail> list = operationStatementMapper.getMarketingDetailList(condition);
		if(list!=null){
			for (MarketingDetail md : list) {
				md.setPtSubsidyAmt(NumberUtils.RMBCentToYuan(md.getPtSubsidyAmt()));
			}
		}
		return list;
	}

	@Override
	public PageInfo<MarketingDetail> getMarketingDetailPage(int startNum, int pageSize, Condition condition) {
		PageHelper.startPage(startNum, pageSize);
		List<MarketingDetail> list = getMarketingDetailList(condition);
		PageInfo<MarketingDetail> page = new PageInfo<MarketingDetail>(list);
		return page;
	}

	@Override
	public OperationDetail getOperationDetailAmount(Condition condition) {
		OperationDetail od = operationStatementMapper.getOperationDetailAmount(condition);
		if(od!=null){
			if(od.getMemberCardConsumeAmt()!=null){
				od.setMemberCardConsumeAmt(NumberUtils.RMBCentToYuan(od.getMemberCardConsumeAmt()));
			}else{
				od.setMemberCardConsumeAmt(NumberUtils.RMBCentToYuan("0"));
			}
			if(od.getMemberCardConsumeCount()==null){
				od.setMemberCardConsumeCount("0");
			}
			if(od.getWxConsumeAmt()!=null){
				od.setWxConsumeAmt(NumberUtils.RMBCentToYuan(od.getWxConsumeAmt()));
			}else{
				od.setWxConsumeAmt(NumberUtils.RMBCentToYuan("0"));
			}
			if(od.getWxConsumeCount()==null){
				od.setWxConsumeCount("0");
			}
			if(od.getJfConsumeAmt()!=null){
				od.setJfConsumeAmt(NumberUtils.RMBCentToYuan(od.getJfConsumeAmt()));
			}else{
				od.setJfConsumeAmt(NumberUtils.RMBCentToYuan("0"));
			}
			if(od.getJfConsumeCount()==null){
				od.setJfConsumeCount("0");
			}
			if(od.getWxRechargeUploadAmt()!=null){
				od.setWxRechargeUploadAmt(NumberUtils.RMBCentToYuan(od.getWxRechargeUploadAmt()));
			}else{
				od.setWxRechargeUploadAmt(NumberUtils.RMBCentToYuan("0"));
			}
			if(od.getWxRechargeTransAmt()!=null){
				od.setWxRechargeTransAmt(NumberUtils.RMBCentToYuan(od.getWxRechargeTransAmt()));
			}else{
				od.setWxRechargeTransAmt(NumberUtils.RMBCentToYuan("0"));
			}
			if(od.getWxRechargeCount()==null){
				od.setWxRechargeCount("0");
			}
			if(od.getJfRechargeUploadAmt()!=null){
				od.setJfRechargeUploadAmt(NumberUtils.RMBCentToYuan(od.getJfRechargeUploadAmt()));
			}else{
				od.setJfRechargeUploadAmt(NumberUtils.RMBCentToYuan("0"));
			}
			if(od.getJfRechargeTransAmt()!=null){
				od.setJfRechargeTransAmt(NumberUtils.RMBCentToYuan(od.getJfRechargeTransAmt()));
			}else{
				od.setJfRechargeTransAmt(NumberUtils.RMBCentToYuan("0"));
			}
			if(od.getJfRechargeCount()==null){
				od.setJfRechargeCount("0");
			}
			if(od.getPtRechargeUploadAmt()!=null){
				od.setPtRechargeUploadAmt(NumberUtils.RMBCentToYuan(od.getPtRechargeUploadAmt()));
			}else{
				od.setPtRechargeUploadAmt(NumberUtils.RMBCentToYuan("0"));
			}
			if(od.getPtRechargeTransAmt()!=null){
				od.setPtRechargeTransAmt(NumberUtils.RMBCentToYuan(od.getPtRechargeTransAmt()));
			}else{
				od.setPtRechargeTransAmt(NumberUtils.RMBCentToYuan("0"));
			}
			if(od.getPtRechargeCount()==null){
				od.setPtRechargeCount("0");
			}
			if(od.getMemberCardBal()!=null){
				od.setMemberCardBal(NumberUtils.RMBCentToYuan(od.getMemberCardBal()));
			}else{
				od.setMemberCardBal(NumberUtils.RMBCentToYuan("0"));
			}
		}
		return od;
	}

}
