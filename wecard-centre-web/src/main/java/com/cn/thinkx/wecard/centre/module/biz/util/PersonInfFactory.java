package com.cn.thinkx.wecard.centre.module.biz.util;

import com.cn.thinkx.wecard.centre.module.biz.service.PersonInfService;

public class PersonInfFactory {
	private PersonInfFactory() {
		
	}

	private static PersonInfService personInfService;

	public static PersonInfService getPersonInfService() {
		if (personInfService == null) {
			personInfService = SpringContextUtil.getBeanById("personInfService");
		}
		return personInfService;
	}

}
