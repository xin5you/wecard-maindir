package com.cn.thinkx.facade.service;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.facade.bean.IntegrationPayRequest;
import com.cn.thinkx.facade.bean.base.BaseResp;

public class IntegrationPayFacadeMock implements IntegrationPayFacade{

	@Override
	public String payMentTransactionITF(IntegrationPayRequest req) throws Exception {
		BaseResp resp = new BaseResp();
		resp.setCode("99");
		return JSONArray.toJSONString(resp);
	}

	@Override
	public String refundTransactionITF(IntegrationPayRequest req) throws Exception {
		BaseResp resp = new BaseResp();
		resp.setCode("99");
		return JSONArray.toJSONString(resp);
	}

	@Override
	public String queryTransactionITF(IntegrationPayRequest req) throws Exception {
		BaseResp resp = new BaseResp();
		resp.setCode("99");
		return JSONArray.toJSONString(resp);
//		return null;
	}

}
