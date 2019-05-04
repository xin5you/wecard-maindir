package com.cn.thinkx.oms.module.sys.model;

import java.util.Date;


public class User implements java.io.Serializable {

	protected Integer id;
	private String loginname; // 登录名
	private String password; // 密码
	private String name; // 姓名
	private String sex; // 性别
	private String age; // 年龄
	private Date createdatetime; // 创建时间
	private String usertype; // 用户类型
	private String isdefault; // 是否默认
	private String state; // 状态
	private String organizationId;
	private int lockVersion;
	
	private String roleId;
	
	private String organizationName;
	
	public Integer getId() {
		return id;
	}
	public String getLoginname() {
		return loginname;
	}
	public String getPassword() {
		return password;
	}
	public String getName() {
		return name;
	}
	public String getSex() {
		return sex;
	}
	public String getAge() {
		return age;
	}
	public Date getCreatedatetime() {
		return createdatetime;
	}
	public String getUsertype() {
		return usertype;
	}
	public String getIsdefault() {
		return isdefault;
	}
	public String getState() {
		return state;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public int getLockVersion() {
		return lockVersion;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public void setLockVersion(int lockVersion) {
		this.lockVersion = lockVersion;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}