package com.cn.thinkx.oms.module.sys.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.sys.mapper.OrganizationMapper;
import com.cn.thinkx.oms.module.sys.model.Organization;
import com.cn.thinkx.oms.module.sys.service.OrganizationService;

/**
 * 组织机构 部门表
 * @author zqy
 *
 */
@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService {
	
    @Autowired
    @Qualifier("organizationMapper")
    private OrganizationMapper organizationMapper;

	@Override
	public int saveOrganization(Organization entity) {
		return organizationMapper.saveOrganization(entity);
	}

	@Override
	public int updateOrganization(Organization entity) {
		return organizationMapper.updateOrganization(entity);
	}

	@Override
	public int deleteOrganization(String organId) {
		return organizationMapper.deleteOrganization(organId);
	}

	@Override
	public Organization getOrganizationById(String organId) {
		return organizationMapper.getOrganizationById(organId);
	}

	@Override
	public List<Organization> getOrganizationList(Organization entity) {
		return organizationMapper.getOrganizationList(entity);
	}

}
