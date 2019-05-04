package com.cn.thinkx.oms.module.active.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.active.mapper.CommodityInfMapper;
import com.cn.thinkx.oms.module.active.model.CommodityInf;
import com.cn.thinkx.oms.module.active.service.CommodityInfService;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("commodityInfService")
public class CommodityInfSerivceImpl implements CommodityInfService {

	@Autowired
	@Qualifier("commodityInfMapper")
	private CommodityInfMapper commodityInfMapper;

	@Override
	public String addCommodityInf(CommodityInf commodityInf) {
		commodityInfMapper.insertCommodityInf(commodityInf);
		return commodityInf.getCommodityId();
	}

	@Override
	public int updateCommodityInf(CommodityInf entity) {
		return commodityInfMapper.updateCommodityInf(entity);
	}

	@Override
	public CommodityInf getCommodityInfById(String commId) {
		return commodityInfMapper.getCommodityInfById(commId);
	}
	
	@Override
	public List<CommodityInf> getCommoListByMchntId(String mchntId) {
		List<CommodityInf> list = commodityInfMapper.getCommoListByMchntId(mchntId);
		if (list != null && list.size() > 0) {
			for (CommodityInf obj : list) {
				obj.setCommodityFacevalue(NumberUtils.RMBCentToYuan(obj.getCommodityFacevalue()));
				obj.setCommodityCost(NumberUtils.RMBCentToYuan(obj.getCommodityCost()));
			}
		}
		return list;
	}

	@Override
	public List<CommodityInf> findCommodityInfList(CommodityInf entity) {
		return commodityInfMapper.getCommodityInfList(entity);
	}

	@Override
	public int deleteCommodityInf(String commId) {
		return commodityInfMapper.deleteCommodityInf(commId);
	}

	/**
	 * 商品列表
	 * 
	 * @param startNum
	 * @param pageSize
	 * @param user
	 * @return
	 */
	public PageInfo<CommodityInf> getCommodityInfPage(int startNum, int pageSize, CommodityInf entity) {
		PageHelper.startPage(startNum, pageSize);
		List<CommodityInf> list = findCommodityInfList(entity);
		PageInfo<CommodityInf> page = new PageInfo<CommodityInf>(list);
		return page;
	}

}
