package com.cn.thinkx.oms.module.city.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.city.mapper.CityInfMapper;
import com.cn.thinkx.oms.module.city.model.CityInf;
import com.cn.thinkx.oms.module.city.service.CityInfService;



@Service("cityInfService")
public class CityInfServiceImpl implements CityInfService {

	@Autowired
	@Qualifier("cityInfMapper")
	private CityInfMapper cityInfMapper;

	@Override
	public List<CityInf> findCityInfList(CityInf entity) {
		
		return cityInfMapper.findCityInfList(entity);
	}

	public CityInf getCityInfById(String cId){
		return cityInfMapper.getCityInfById(cId);
	}
	
}
