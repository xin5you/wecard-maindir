package com.cn.thinkx.oms.module.merchant.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.merchant.mapper.MerchantInfMapper;
import com.cn.thinkx.oms.module.merchant.model.InsInf;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.model.MerchantInfList;
import com.cn.thinkx.oms.module.merchant.model.ShopInf;
import com.cn.thinkx.oms.module.merchant.service.InsInfService;
import com.cn.thinkx.oms.module.merchant.service.MerchantContractService;
import com.cn.thinkx.oms.module.merchant.service.MerchantInfListService;
import com.cn.thinkx.oms.module.merchant.service.MerchantInfService;
import com.cn.thinkx.oms.module.merchant.service.ShopInfService;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;




@Service("merchantInfService")
public class MerchantInfServiceImpl implements MerchantInfService {
	
	@Autowired
	@Qualifier("merchantInfMapper")
	private MerchantInfMapper merchantInfMapper;
	
	@Autowired
	@Qualifier("shopInfService")
	private ShopInfService shopInfService;
	
	
	@Autowired
	@Qualifier("merchantInfListService")
	private MerchantInfListService merchantInfListService;
	
	
	@Autowired
	@Qualifier("insInfService")
	private InsInfService insInfService;
	
	@Autowired
	@Qualifier("merchantContractService")
	private MerchantContractService merchantContractService;
	
	
	/**
	 * 查找商户信息
	 * @param mchntCode 商户号
	 * @return
	 */
	public MerchantInf getMerchantInfById(String mId){
		return merchantInfMapper.getMerchantInfById(mId);
	}


	/**
	 * 保存商户信息
	 * @param merchantId
	 * @return
	 */
	 public Map<String,Object> insertMerchantInf(MerchantInf merchantInf,MerchantInfList merchantInfList,ShopInf shopInf){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("status", Boolean.FALSE.booleanValue());
		/***添加机构**/
		InsInf insInf=new InsInf();
		insInf.setInsName(merchantInf.getMchntName());
		insInf.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
		insInfService.addInsInf(insInf);
		
		/***添加商户**/
		merchantInf.setInsId(insInf.getInsId());
		merchantInf.setSoldCount(0);
		merchantInf.setAccountStat("00");
		merchantInf.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
		merchantInfMapper.insertMerchantInf(merchantInf);
		
		
		/**添加商户明细*/
		merchantInfList.setInsId(insInf.getInsId());
		merchantInfList.setMchntId(merchantInf.getMchntId());
		merchantInfList.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
		merchantInfListService.insertMerchantInfoList(merchantInfList);
		
		/**添加门店*/

		shopInf.setMchntId(merchantInf.getMchntId());
		shopInf.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
		shopInfService.addShopInf(shopInf);
		
		/**添加商户合同*/
		merchantContractService.insertDefaultMerchantContract(merchantInf);
		
		map.put("mchntId", merchantInf.getMchntId());
		map.put("shopId", shopInf.getShopId());
		map.put("status", Boolean.TRUE.booleanValue());
		return map;
	}


	/**
	 * 修改商户信息
	 * @param merchantId
	 * @return
	 */
	public int updateMerchantInf(MerchantInf entity,MerchantInfList merchantInfList){
		 int oper=0;
		 oper=merchantInfMapper.updateMerchantInf(entity);
		 oper=merchantInfListService.updateMerchantInfoList(merchantInfList);
		 return oper;
	}


	/**
	 * 删除商户信息
	 * @param merchantId
	 * @return
	 */
	public int deleteMerchantInf(String mchntId) {
		int res=0;
		res= merchantInfMapper.deleteMerchantInf(mchntId);
		res= shopInfService.deleteShopInfByMchntId(mchntId);
		res= merchantInfListService.deleteMerchantInfList(mchntId);
		return res;
	}
	
	/**
	 * 查询所有用户
	 */
	public List<MerchantInf> getMerchantInfList(MerchantInf entity){
		return merchantInfMapper.getMerchantInfList(entity);
	}
	/**
	 * 用户列表
	 * @param startNum
	 * @param pageSize
	 * @param MerchantInf
	 * @return
	 */
    public PageInfo<MerchantInf> getMerchantInfPage(int startNum, int pageSize, MerchantInf MerchantInf){
		PageHelper.startPage(startNum, pageSize);
		List<MerchantInf> list = getMerchantInfList(MerchantInf);
		for (MerchantInf merchantInf2 : list) {
			merchantInf2.setMchntType(BaseConstants.MchntTypeEnum.findMchntType(merchantInf2.getMchntType()));
		}
		PageInfo<MerchantInf> page = new PageInfo<MerchantInf>(list);
		return page;
    }
    
	/**
	 * 获取商户下拉选择列表
	 * @return
	 */
	public List<MerchantInf> getMerchantInfListBySelect(){
		return merchantInfMapper.getMerchantInfListBySelect();
	}
	
	public List<MerchantInf> getMchntProList(){
		return merchantInfMapper.getMchntProList();
	}


	@Override
	public MerchantInf getMerchantInfByMchntCode(String mchntCode) {
		return merchantInfMapper.getMerchantInfByMchntCode(mchntCode);
	}

}
