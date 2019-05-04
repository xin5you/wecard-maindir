package com.cn.thinkx.oms.module.city.service;

import java.util.List;

import com.cn.thinkx.oms.module.city.model.CityInf;

/**
 * 行业类型service
 * @author zqy
 *
 */
public interface CityInfService {


	public List<CityInf> findCityInfList(CityInf entity);
	
	public CityInf getCityInfById(String cId);
}
