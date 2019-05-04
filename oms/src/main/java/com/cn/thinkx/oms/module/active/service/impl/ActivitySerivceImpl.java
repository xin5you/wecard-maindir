package com.cn.thinkx.oms.module.active.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.active.mapper.ActivityMapper;
import com.cn.thinkx.oms.module.active.model.ActiveList;
import com.cn.thinkx.oms.module.active.model.CommodityInf;
import com.cn.thinkx.oms.module.active.model.MerchantActiveInf;
import com.cn.thinkx.oms.module.active.service.ActivityService;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("activityService")
public class ActivitySerivceImpl implements ActivityService {

	@Autowired
	@Qualifier("activityMapper")
	private ActivityMapper activityMapper;

	@Override
	public String addMerchantActiveInf(MerchantActiveInf entity) {
		activityMapper.insertMerchantActiveInf(entity);
		return entity.getActiveId();
	}
	
	@Override
	public String addActiveList(ActiveList entity) {
		activityMapper.insertActiveList(entity);
		return entity.getActiveListId();
	}

	@Override
	public int updateMerchantActiveInf(MerchantActiveInf entity) {
		return activityMapper.updateMerchantActiveInf(entity);
	}

	@Override
	public int updateActiveList(ActiveList entity) {
		return activityMapper.updateActiveList(entity);
	}

	@Override
	public MerchantActiveInf getMerchantActiveInfById(String id) {
		return activityMapper.getMerchantActiveInfById(id);
	}

	@Override
	public ActiveList getActiveListById(String id) {
		return activityMapper.getActiveListById(id);
	}

	@Override
	public List<CommodityInf> getCommoListByActiveId(String activeId) {
		List<CommodityInf> list = activityMapper.getCommoListByActiveId(activeId);
		if (list != null && list.size() > 0) {
			for (CommodityInf obj : list) {
				obj.setCommodityFacevalue(NumberUtils.RMBCentToYuan(obj.getCommodityFacevalue()));
				obj.setCommodityCost(NumberUtils.RMBCentToYuan(obj.getCommodityCost()));
				obj.setSellingPrice(NumberUtils.RMBCentToYuan(obj.getSellingPrice()));
			}
		}
		return list;
	}

	@Override
	public int deleteMerchantActiveInf(MerchantActiveInf entity) {
		return activityMapper.deleteMerchantActiveInf(entity);
	}

	@Override
	public int deleteActiveList(ActiveList entity) {
		return activityMapper.deleteActiveList(entity);
	}

	public PageInfo<MerchantActiveInf> getMerchantActiveInfPage(int startNum, int pageSize, MerchantActiveInf entity) {
		PageHelper.startPage(startNum, pageSize);
		List<MerchantActiveInf> list = activityMapper.getMerchantActiveInfList(entity);
		PageInfo<MerchantActiveInf> page = new PageInfo<MerchantActiveInf>(list);
		return page;
	}

}
