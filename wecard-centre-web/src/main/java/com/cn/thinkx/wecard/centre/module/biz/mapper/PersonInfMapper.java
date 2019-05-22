package com.cn.thinkx.wecard.centre.module.biz.mapper;

import com.cn.thinkx.common.wecard.domain.person.PersonInf;
import org.apache.ibatis.annotations.Param;

public interface PersonInfMapper {

	PersonInf getPersonInfByUserId(String userId);
	
}