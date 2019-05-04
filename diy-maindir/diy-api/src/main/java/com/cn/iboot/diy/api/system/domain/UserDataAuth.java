package com.cn.iboot.diy.api.system.domain;
import com.cn.iboot.diy.api.base.domain.BaseEntity;

public class UserDataAuth extends BaseEntity {
	
	private static final long serialVersionUID = -4688626183004316392L;
	
    private String authId;

    private String userId;

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId == null ? null : authId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

}