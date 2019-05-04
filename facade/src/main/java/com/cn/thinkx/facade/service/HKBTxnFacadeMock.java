package com.cn.thinkx.facade.service;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.facade.bean.CardBalQueryRequest;
import com.cn.thinkx.facade.bean.CardTransDetailQueryRequest;
import com.cn.thinkx.facade.bean.CusAccListQueryRequest;
import com.cn.thinkx.facade.bean.CusAccOpeningRequest;
import com.cn.thinkx.facade.bean.CusAccQueryRequest;
import com.cn.thinkx.facade.bean.CusCardOpeningRequest;
import com.cn.thinkx.facade.bean.CustomerBuyCardStocksRequest;
import com.cn.thinkx.facade.bean.MchntInfQueryRequest;
import com.cn.thinkx.facade.bean.RechargeTransRequest;
import com.cn.thinkx.facade.bean.ShopInfQueryRequest;
import com.cn.thinkx.facade.bean.base.BaseTxnReq;
import com.cn.thinkx.facade.bean.base.BaseResp;

public class HKBTxnFacadeMock implements HKBTxnFacade {

	@Override
	public String rechargeTransactionITF(RechargeTransRequest req) throws Exception {
		BaseResp resp = new BaseResp();
		resp.setCode("99");
		return JSONArray.toJSONString(resp);
	}

	@Override
	public String customerAccountOpeningITF(CusAccOpeningRequest req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String customerCardOpeningITF(CusCardOpeningRequest req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String customerHKBCardOpeningITF(CusCardOpeningRequest req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String customerAccountQueryITF(CusAccQueryRequest req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String cardBalanceQueryITF(CardBalQueryRequest req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String hkbConsumeTransactionITF(BaseTxnReq req) throws Exception {
		BaseResp resp = new BaseResp();
		resp.setCode("99");
		return JSONArray.toJSONString(resp);
	}

	@Override
	public String quickPaymentTransactionITF(BaseTxnReq req) throws Exception {
		BaseResp resp = new BaseResp();
		resp.setCode("99");
		return JSONArray.toJSONString(resp);
	}

	@Override
	public String mchtSellingCardListQueryITF(BaseTxnReq req) throws Exception {
		BaseResp resp = new BaseResp();
		resp.setCode("99");
		return JSONArray.toJSONString(resp);
	}

	@Override
	public String customerAccListQueryITF(CusAccListQueryRequest req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String shopInfoQueryITF(ShopInfQueryRequest req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String shopListQueryITF(ShopInfQueryRequest req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String merchantInfoQueryITF(MchntInfQueryRequest req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String cardTransDetailQueryITF(CardTransDetailQueryRequest req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String transExceptionQueryITF(BaseTxnReq req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String customerBuyCardStocksQueryITF(CustomerBuyCardStocksRequest req) throws Exception {
		BaseResp resp = new BaseResp();
		resp.setCode("99");
		return JSONArray.toJSONString(resp);
	}

	@Override
	public String getMchtSellingCardListQueryITF(BaseTxnReq req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getShopInfoQueryITF(ShopInfQueryRequest req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getShopListQueryITF(ShopInfQueryRequest req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMerchantInfoQueryITF(BaseTxnReq req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
