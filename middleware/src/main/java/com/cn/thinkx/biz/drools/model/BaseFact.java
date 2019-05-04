package com.cn.thinkx.biz.drools.model;

import java.io.Serializable;

public class BaseFact implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 规则的过期时间
	 */
	private String expires;
	/**
	 * 规则的生效时间
	 */
	private String effective;
	/**
	 * 规则定时(单位：毫秒)
	 */
	private String duration;
	/**
	 * 优先级
	 */
	private String salience;

	public String getExpires() {
		return expires;
	}

	public void setExpires(String expires) {
		this.expires = expires;
	}

	public String getEffective() {
		return effective;
	}

	public void setEffective(String effective) {
		this.effective = effective;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getSalience() {
		return salience;
	}

	public void setSalience(String salience) {
		this.salience = salience;
	}

}
