package com.cn.thinkx.oms.module.phoneRecharge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.phoneRecharge.mapper.PhoneRechargeShopMapper;
import com.cn.thinkx.oms.module.phoneRecharge.model.PhoneRechargeShop;
import com.cn.thinkx.oms.module.phoneRecharge.service.PhoneRechargeShopService;
import com.cn.thinkx.oms.util.NumberUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants.IsUsableType;
import com.cn.thinkx.pms.base.utils.BaseConstants.OperatorType;
import com.cn.thinkx.pms.base.utils.BaseConstants.ShopType;
import com.cn.thinkx.pms.base.utils.BaseConstants.ShopUnitType;
import com.cn.thinkx.pms.base.utils.BaseConstants.phoneRechargeSupplier;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("phoneRechargeShopService")
public class PhoneRechargeShopServiceImpl implements PhoneRechargeShopService {
	
	@Autowired
	private PhoneRechargeShopMapper phoneRechargeShopMapper;

	@Override
	public List<PhoneRechargeShop> getPhoneRechargeShopList(PhoneRechargeShop pps) {
		List<PhoneRechargeShop> list = phoneRechargeShopMapper.getPhoneRechargeShopList(pps);
		return list;
	}

	@Override
	public int insertPhoneRechargeShop(PhoneRechargeShop pps) {
		PhoneRechargeShop prs = phoneRechargeShopMapper.getPhoneRechargeShopByPhoneRechargeShop(pps);
		if (prs == null) {
			return phoneRechargeShopMapper.insertPhoneRechargeShop(pps);
		}
		return 1;
	}

	@Override
	public int updatePhoneRechargeShop(PhoneRechargeShop pps) {
		return phoneRechargeShopMapper.updatePhoneRechargeShop(pps);
	}

	@Override
	public int deletePhoneRechargeShop(String id) {
		return phoneRechargeShopMapper.deletePhoneRechargeShop(id);
	}

	@Override
	public PageInfo<PhoneRechargeShop> getPhoneRechargeShopPage(int startNum, int pageSize, PhoneRechargeShop entity) {
		try {
			PageHelper.startPage(startNum, pageSize);
			List<PhoneRechargeShop> list = getPhoneRechargeShopList(entity);
			for (PhoneRechargeShop pps : list) {
				if(!StringUtil.isNullOrEmpty(pps.getSupplier())){
					pps.setSupplier(phoneRechargeSupplier.findByCode(pps.getSupplier()).getValue());
				}
				if(!StringUtil.isNullOrEmpty(pps.getOper())){
					pps.setOper(OperatorType.findByCode(pps.getOper()));
				}
				if(!StringUtil.isNullOrEmpty(pps.getShopType())){
					pps.setShopType(ShopType.findByCode(pps.getShopType()));
				}
				if(!StringUtil.isNullOrEmpty(pps.getIsUsable())){
					pps.setIsUsable(IsUsableType.findByCode(pps.getIsUsable()).getValue());
				}
				if(!StringUtil.isNullOrEmpty(pps.getShopPrice())){
					pps.setShopPrice(NumberUtils.RMBCentToYuan(pps.getShopPrice()));
				}
				if(!StringUtil.isNullOrEmpty(pps.getResv1())){
					pps.setResv1(ShopUnitType.findByCode(pps.getResv1()));
				}
			}
			PageInfo<PhoneRechargeShop> page = new PageInfo<PhoneRechargeShop>(list);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PhoneRechargeShop getPhoneRechargeShopById(String id) {
		return phoneRechargeShopMapper.getPhoneRechargeShopById(id);
	}

	@Override
	public List<PhoneRechargeShop> getShopFaceByPhoneRechargeShop(PhoneRechargeShop pps) {
		return phoneRechargeShopMapper.getShopFaceByPhoneRechargeShop(pps);
	}

	@Override
	public List<PhoneRechargeShop> getYDShopFaceByPhoneRechargeShop(PhoneRechargeShop pps) {
		return phoneRechargeShopMapper.getYDShopFaceByPhoneRechargeShop(pps);
	}

	@Override
	public List<PhoneRechargeShop> getLTShopFaceByPhoneRechargeShop(PhoneRechargeShop pps) {
		return phoneRechargeShopMapper.getLTShopFaceByPhoneRechargeShop(pps);
	}

	@Override
	public List<PhoneRechargeShop> getDXShopFaceByPhoneRechargeShop(PhoneRechargeShop pps) {
		return phoneRechargeShopMapper.getDXShopFaceByPhoneRechargeShop(pps);
	}

	@Override
	public PhoneRechargeShop getPhoneRechargeShopByPhoneRechargeShop(PhoneRechargeShop pps) {
		return phoneRechargeShopMapper.getPhoneRechargeShopByPhoneRechargeShop(pps);
	}

}
