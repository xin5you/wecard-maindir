package com.cn.thinkx.oms.module.sys.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.sys.model.Organization;

@Repository("organizationMapper")
public interface OrganizationMapper{
	
	 int saveOrganization(Organization entity);
	 
	 int updateOrganization(Organization entity);
	 
	 int deleteOrganization(String organId);
	 
	 Organization getOrganizationById(String organId);
	 
	 List<Organization> getOrganizationList(Organization entity);
	
}
