package com.cn.iboot.diy.api.system.domain;
import com.cn.iboot.diy.api.base.domain.BaseEntity;

public class RoleResource extends BaseEntity {
  
	private static final long serialVersionUID = -4688626183004316392L;
	
	private String roleId;

    private String resourceId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId == null ? null : resourceId.trim();
    }

}