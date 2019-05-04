package com.cn.thinkx.oms.module.statement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.statement.mapper.FinancingStatementMapper;
import com.cn.thinkx.oms.module.statement.model.FinancingSummarizing;
import com.cn.thinkx.oms.module.statement.service.FinancingStatementService;
import com.cn.thinkx.oms.module.statement.util.ComputeUtil;
import com.cn.thinkx.oms.module.statement.util.Condition;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("financingStatementService")
public class FinancingStatementServiceImpl implements FinancingStatementService {

	@Autowired
	@Qualifier("financingStatementMapper")
	private FinancingStatementMapper financingStatementMapper;

	@Override
	public List<FinancingSummarizing> getFinancingSummarizingList(Condition condition) {
		List<FinancingSummarizing> list = financingStatementMapper.getFinancingSummarizingList(condition);
		if(list!=null){
			for (FinancingSummarizing fs : list) {
				Integer settleAmt = ComputeUtil.toCompute(fs.getSettleAmt(), fs.getServiceCharge());
				fs.setWxConsumeAmt(NumberUtils.RMBCentToYuan(fs.getWxConsumeAmt()));
				fs.setJfConsumeAmt(NumberUtils.RMBCentToYuan(fs.getJfConsumeAmt()));
				fs.setWxRechargeUploadAmt(NumberUtils.RMBCentToYuan(fs.getWxRechargeUploadAmt()));
				fs.setJfRechargeUploadAmt(NumberUtils.RMBCentToYuan(fs.getJfRechargeUploadAmt()));
				fs.setPtRechargeUploadAmt(NumberUtils.RMBCentToYuan(fs.getPtRechargeUploadAmt()));
				fs.setPtSubsidyAmt(NumberUtils.RMBCentToYuan(fs.getPtSubsidyAmt()));
				fs.setServiceCharge(NumberUtils.RMBCentToYuan(fs.getServiceCharge()));
				fs.setSettleAmt(NumberUtils.RMBCentToYuan(settleAmt));
			}
		}
		return list;
	}

	@Override
	public PageInfo<FinancingSummarizing> getFinancingSummarizingPage(int startNum, int pageSize, Condition condition) {
		PageHelper.startPage(startNum, pageSize);
		List<FinancingSummarizing> list = getFinancingSummarizingList(condition);
		PageInfo<FinancingSummarizing> page = new PageInfo<FinancingSummarizing>(list);
		return page;
	}

}
