package com.cn.thinkx.oms.module.margin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.margin.mapper.MerchantCashManageMapper;
import com.cn.thinkx.oms.module.margin.model.MerchantCashManage;
import com.cn.thinkx.oms.module.margin.service.MerchantCashManageService;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.service.MerchantInfService;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("merchantCashManageService")
public class MerchantCashManageServiceImpl implements MerchantCashManageService {

	@Autowired
	@Qualifier("merchantCashManageMapper")
	private MerchantCashManageMapper merchantCashManageMapper;

	@Autowired
	@Qualifier("merchantInfService")
	private MerchantInfService merchantInfService;

	public MerchantCashManage getMerchantCashManageById(String marginId) {
		return merchantCashManageMapper.getMchntCashManagetById(marginId);
	}
	
	public int insertMerchantCashManage(MerchantCashManage entity){
		int oper=0;
		MerchantCashManage m=this.getMerchantCashManageByMchntId(entity.getMchntId());
		if(m ==null){
//			insInfService.getInsInfById(id);
			MerchantInf merchantInf =merchantInfService.getMerchantInfById(entity.getMchntId());
			entity.setInsId(merchantInf.getInsId());
			entity.setMortgageAmt("0"); //押款金额
			entity.setGetQuota("0");//获取额度
			entity.setRechargeAmt("0");//累计充值金额
			entity.setRechargeFaceAmt("0");//累计充值面额
			entity.setCashThresholdAmt("0");////提现阀值
			entity.setGrossProfitRate("0");//毛利率
			entity.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
			oper= merchantCashManageMapper.insertMchntCashManaget(entity);
		}
		return oper;
	}

	public int updateMerchantCashManage(MerchantCashManage entity) {
		
		return merchantCashManageMapper.updateMchntCashManage(entity);
	}


	public List<MerchantCashManage> getMerchantCashManageList(MerchantCashManage entity) {
		
		return merchantCashManageMapper.getMerchantCashManageList(entity);
	}


	@Override
	public PageInfo<MerchantCashManage> getMerchantCashManagePage(int startNum, int pageSize,
			MerchantCashManage entity) {
		PageHelper.startPage(startNum, pageSize);
		List<MerchantCashManage> list = getMerchantCashManageList(entity);
		PageInfo<MerchantCashManage> page = new PageInfo<MerchantCashManage>(list);
		return page;
	}

	/**
	 * 查询商户保证金记录
	 * @param marginId
	 * @return
	 */
	public MerchantCashManage getMerchantCashManageByMchntId(String mchntId){
		return merchantCashManageMapper.getMerchantCashManageByMchntId(mchntId);
	}

}
