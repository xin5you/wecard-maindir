package com.cn.thinkx.oms.module.merchant.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.merchant.mapper.ShopInfMapper;
import com.cn.thinkx.oms.module.merchant.model.ShopInf;
import com.cn.thinkx.oms.module.merchant.service.ShopInfService;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.GeoHash;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;



@Service("shopInfService")
public class ShopInfSerivceImpl implements ShopInfService {

	@Autowired
	@Qualifier("shopInfMapper")
	private ShopInfMapper shopInfMapper;

	@Override
	public String addShopInf(ShopInf shopInf) {

		if(!StringUtils.isNullOrEmpty(shopInf.getLatitude()) && !StringUtils.isNullOrEmpty(shopInf.getLongitude())){
			GeoHash g=new GeoHash(Double.parseDouble(shopInf.getLatitude()), Double.parseDouble(shopInf.getLongitude()));
			shopInf.setGeohash(g.getGeoHashBase32());
		}
		shopInfMapper.insertShopInf(shopInf);
		
		return shopInf.getShopId();

	}

	@Override
	public int updateShopInf(ShopInf entity) {
		return shopInfMapper.updateShopInf(entity);
	}

	

	@Override
	public ShopInf getShopInfById(String shopInfId) {
		return shopInfMapper.getShopInfById(shopInfId);
	}

	@Override
	public List<ShopInf> findShopInfList(ShopInf entity) {
		return shopInfMapper.getShopInfList(entity);
	}

	@Override
	public int deleteShopInf(String shopInfId) {

		return shopInfMapper.deleteShopInf(shopInfId);
	}

	@Override
	public int deleteShopInfByMchntId(String mchntId) {
		return shopInfMapper.deleteShopInfByMchntId(mchntId);
	}
	
	/**
	 * 门店列表
	 * @param startNum
	 * @param pageSize
	 * @param user
	 * @return
	 */
    public PageInfo<ShopInf> getShopInfPage(int startNum, int pageSize, ShopInf entity){
    	PageHelper.startPage(startNum, pageSize);
		List<ShopInf> list = findShopInfList(entity);
		for (ShopInf shopInf : list) {
			String pShopCode = shopInf.getpShopCode();
			if(pShopCode!=null){
			ShopInf shop = shopInfMapper.getShopInfByCode(pShopCode);
			if(shop!=null){
			shopInf.setpShopName(shop.getShopName());
			}
			}
		}
		PageInfo<ShopInf> page = new PageInfo<ShopInf>(list);
		return page;
    }

	@Override
	public Map<String, ShopInf> findShopInfListFirstLevel(String id) {
		return shopInfMapper.getShopInfListFirstLevel(id);
	}

	@Override
	public ShopInf getShopInfByCode(String shopCode) {
		return shopInfMapper.getShopInfByCode(shopCode);
	}

	@Override
	public List<ShopInf> getShopInfListByPShopCode(String pShopCode) {
		return shopInfMapper.getShopInfListByPShopCode(pShopCode);
	}

	@Override
	public List<ShopInf> getShopInfListByMchntCode(String mchntCode) {
		return shopInfMapper.getShopInfListByMchntCode(mchntCode);
	}

}
