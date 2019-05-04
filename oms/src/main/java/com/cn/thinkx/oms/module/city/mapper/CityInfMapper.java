package com.cn.thinkx.oms.module.city.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.city.model.CityInf;

@Repository("cityInfMapper")
public interface CityInfMapper {

	/**
	 * 查找城市
	 * @param entity
	 * @return
	 */
	List<CityInf> findCityInfList(CityInf entity);
	
	public CityInf getCityInfById(String cId);
}