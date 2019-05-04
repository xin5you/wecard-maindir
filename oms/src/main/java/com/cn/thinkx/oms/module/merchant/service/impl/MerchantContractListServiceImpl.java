package com.cn.thinkx.oms.module.merchant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.merchant.mapper.MerchantContractListMapper;
import com.cn.thinkx.oms.module.merchant.model.MerchantContract;
import com.cn.thinkx.oms.module.merchant.model.MerchantContractList;
import com.cn.thinkx.oms.module.merchant.model.Product;
import com.cn.thinkx.oms.module.merchant.service.MerchantContractListService;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.DateUtil;



@Service("merchantContractListService")
public class MerchantContractListServiceImpl implements MerchantContractListService {

	@Autowired
	@Qualifier("merchantContractListMapper")
	private MerchantContractListMapper merchantContractListMapper;

	@Override
	public MerchantContractList getMerchantContractListById(String merchantContractListId) {
		return merchantContractListMapper.getMerchantContractListById(merchantContractListId);
	}

	@Override
	public MerchantContractList getMerchantContractListByMerchantId(String merchantContractId) {
		return merchantContractListMapper.getMerchantContractListByMerchantContractId(merchantContractId);
	}

	@Override
	public int insertMerchantContractList(MerchantContractList merchantContractList) {
		return merchantContractListMapper.insertMerchantContractList(merchantContractList);
	}

	@Override
	public int updateMerchantContractList(MerchantContractList merchantContractList) {
		return merchantContractListMapper.updateMerchantContractList(merchantContractList);
	}

	@Override
	public int deleteMerchantContractList(String merchantContractListId) {
		return merchantContractListMapper.deleteMerchantContractList(merchantContractListId);
	}
	
	@Override
	public int insertDefaultMerchantContractList(MerchantContract merchantContract,Product product) {
		
		MerchantContractList merchantContractList = new MerchantContractList();
		//组装合同明细数据
		merchantContractList.setMchntContractId(merchantContract.getMchntContractId());
		merchantContractList.setProductCode(product.getProductCode());
		merchantContractList.setContractType("10");//合同明细类型,用于区分复合卡结算，暂时不用，默认为10无实际意义
		merchantContractList.setContractRate(0);//默认费率为0
		merchantContractList.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());//默认生效
		merchantContractList.setContractStartDate(DateUtil.getCurrentDateStr());
		merchantContractList.setContractEndDate(merchantContract.getContractEndDate());
		merchantContractList.setCreateUser(product.getCreateUser());
		merchantContractList.setUpdateUser(product.getUpdateUser());
		//插入明细合同明细信息
		return this.insertMerchantContractList(merchantContractList);
	}
	
}
