package com.cn.thinkx.service.txn;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.beans.FacadeTxnResp;
import com.cn.thinkx.beans.PayBackBean;
import com.cn.thinkx.beans.TxnPackageBean;

public class Java2TxnBusinessFacadeMock implements Java2TxnBusinessFacade {
	
	@Override
	public String merchantAccountOpeningITF(TxnPackageBean txnBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String customerAccountOpeningITF(TxnPackageBean txnBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String customerPasswordResetITF(TxnPackageBean txnBean) throws Exception {
		FacadeTxnResp resp = new FacadeTxnResp();
		resp.setCode("99");
		return JSONArray.toJSONString(resp);
	}

	@Override
	public String rechargeTransactionITF(TxnPackageBean txnBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String consumeTransactionITF(TxnPackageBean txnBean) throws Exception {
		FacadeTxnResp resp = new FacadeTxnResp();
		resp.setCode("99");
		return JSONArray.toJSONString(resp);
	}

	@Override
	public String merchantWithdrawITF(TxnPackageBean txnBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String HKBPayBackToJF(PayBackBean bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String consumeRefundITF(TxnPackageBean txnBean) throws Exception {
		FacadeTxnResp resp = new FacadeTxnResp();
		resp.setCode("999");
		return JSONArray.toJSONString(resp);
	}

	@Override
	public String transRefundITF(TxnPackageBean txnBean) throws Exception {
		FacadeTxnResp resp = new FacadeTxnResp();
		resp.setCode("999");
		return JSONArray.toJSONString(resp);
	}

	@Override
	public String transExceptionQueryITF(String wxPrimaryKey) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String quickConsumeTransactionITF(TxnPackageBean txnBean) throws Exception{
		FacadeTxnResp resp = new FacadeTxnResp();
		resp.setCode("99");
		return null;
	}
}
