package com.cn.thinkx.oms.module.sys.service;

import java.util.List;

import com.cn.thinkx.oms.module.sys.model.Organization;

public interface OrganizationService {
	
	 int saveOrganization(Organization entity);
	 
	 int updateOrganization(Organization entity);
	 
	 int deleteOrganization(String organId);
	 
	 Organization getOrganizationById(String organId);
	 
	 List<Organization> getOrganizationList(Organization entity);
}
