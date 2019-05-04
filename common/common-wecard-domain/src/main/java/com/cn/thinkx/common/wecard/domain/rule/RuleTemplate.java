package com.cn.thinkx.common.wecard.domain.rule;

import com.cn.thinkx.common.wecard.domain.base.BaseDomain;

public class RuleTemplate extends BaseDomain {

	private String templateCode;
	
	private String templateName;
	
	private String templateDesp;
	
	private String templateFrom;

	private String dataStat;

	public String getTemplateCode() {
		return templateCode;
	}

	public String getTemplateName() {
		return templateName;
	}

	public String getTemplateDesp() {
		return templateDesp;
	}

	public String getTemplateFrom() {
		return templateFrom;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void setTemplateDesp(String templateDesp) {
		this.templateDesp = templateDesp;
	}

	public void setTemplateFrom(String templateFrom) {
		this.templateFrom = templateFrom;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	
}
