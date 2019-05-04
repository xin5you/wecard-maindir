package com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.entity;

import java.util.List;

import com.cn.thinkx.wecard.middleware.resp.domain.entity.BaseResp;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.vo.CardTransDetailQueryITFVo;

public class CardTransDetailQueryITFResp extends BaseResp {

	private String pageSize;
	
	private String currPageSize;

	private List<CardTransDetailQueryITFVo> transList;

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getCurrPageSize() {
		return currPageSize;
	}

	public void setCurrPageSize(String currPageSize) {
		this.currPageSize = currPageSize;
	}

	public List<CardTransDetailQueryITFVo> getTransList() {
		return transList;
	}

	public void setTransList(List<CardTransDetailQueryITFVo> transList) {
		this.transList = transList;
	}
}
