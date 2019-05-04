package com.cn.thinkx.wecard.facade.telrecharge.mapper;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.common.base.core.mapper.BaseMapper;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelProviderInf;

@Repository("telProviderInfMapper")
public interface TelProviderInfMapper extends BaseMapper<TelProviderInf> {

	/**
	 * 修改原来是默认路由的数据为不是默认路由
	 * 
	 * @return
	 */
	int updateByDefaultRoute();
}
