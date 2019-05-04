package com.cn.thinkx.oms.module.merchant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.constants.Constants;
import com.cn.thinkx.oms.module.merchant.mapper.MerchantContractMapper;
import com.cn.thinkx.oms.module.merchant.model.MerchantContract;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.service.MerchantContractService;
import com.cn.thinkx.oms.module.merchant.service.MerchantInfService;
import com.cn.thinkx.pms.base.utils.DateUtil;



@Service("merchantContractService")
public class MerchantContractServiceImpl implements MerchantContractService {

	@Autowired
	@Qualifier("merchantContractMapper")
	private MerchantContractMapper merchantContractMapper;
	
	@Autowired
	@Qualifier("merchantInfService")
	private MerchantInfService merchantInfService;

	@Override
	public MerchantContract getMerchantContractById(String merchantContractId) {
		return merchantContractMapper.getMerchantContractById(merchantContractId);
	}

	@Override
	public MerchantContract getMerchantContractByMerchantId(String mchntId) {
		return merchantContractMapper.getMerchantContractByMerchantId(mchntId);
	}

	@Override
	public int insertMerchantContract(MerchantContract merchantContract) {
		return merchantContractMapper.insertMerchantContract(merchantContract);
	}

	@Override
	public int updateMerchantContract(MerchantContract merchantContract) {
		return merchantContractMapper.updateMerchantContract(merchantContract);
	}

	@Override
	public int deleteMerchantContract(String merchantContractId) {
		return merchantContractMapper.deleteMerchantContract(merchantContractId);
	}
	
	@Override
	public int insertDefaultMerchantContract(MerchantInf merchantInf) {
		if(merchantInf.getMchntCode() == null || "".equals(merchantInf.getMchntCode()))
			merchantInf = merchantInfService.getMerchantInfById(merchantInf.getMchntId());
		MerchantContract merchantContract = new MerchantContract();
		merchantContract.setMchntId(merchantInf.getMchntId());
		merchantContract.setMchntCode(merchantInf.getMchntCode());
		merchantContract.setSettleType(Constants.SettleTypeEnum.SETTLESUM.getCode());
		merchantContract.setSettleCycle(Constants.SettleCycleEnum.SETTLEDAY.getCode());
		merchantContract.setContractStartDate(DateUtil.getCurrentDateStr());
		merchantContract.setContractEndDate("29991231");//无限期
		merchantContract.setPreSettleDate(DateUtil.getStringFromDate(DateUtil.getBeforeDate(DateUtil.getCurrentDate(), 1), "yyyyMMdd"));
		merchantContract.setDataStat("0");//默认有效
		merchantContract.setCreateUser(merchantInf.getCreateUser());
		merchantContract.setUpdateUser(merchantInf.getUpdateUser());
		
		return this.insertMerchantContract(merchantContract);
	}
}
