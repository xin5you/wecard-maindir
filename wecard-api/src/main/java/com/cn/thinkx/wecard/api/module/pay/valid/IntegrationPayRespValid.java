package com.cn.thinkx.wecard.api.module.pay.valid;

import com.cn.thinkx.common.redis.util.IntSignUtil;
import com.cn.thinkx.common.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.api.module.pay.resp.IntegrationPayResp;
import com.cn.thinkx.wecard.api.module.pay.resp.PPScanCodeResp;
import com.cn.thinkx.wecard.api.module.pay.utils.PPBoxConstants;


/**
 * paipai盒子支付请求验证
 * @author zqy
 *
 */
public class IntegrationPayRespValid {

	public static boolean integrationPayValid(IntegrationPayResp req,PPScanCodeResp resp) {
		if (StringUtil.isNullOrEmpty(req.getCode())) {
			resp.setMsg("返回码为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSwtFlowNo())) {
			resp.setMsg("交易流水号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTermSwtFlowNo())) {
			resp.setMsg("终端交易流水号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTransDate())) {
			resp.setMsg("交易时间为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTransAmt())) {
			resp.setMsg("交易金额为空");
			return true;
		}
//		if (StringUtil.isNullOrEmpty(req.getPaymentType())) {
//			resp.setMsg("支付方式为空");
//			return true;
//		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setSub_code(PPBoxConstants.PPBoxSubCode.SIGNERROR.getCode());
			resp.setMsg("签名为空");
			return true;
		}
		if(!IntSignUtil.genSign(req,RedisDictProperties.getInstance().getdictValueByCode("INT_KEY")).equals(req.getSign())){
			resp.setSub_code(PPBoxConstants.PPBoxSubCode.SIGNERROR.getCode());
			resp.setMsg("签名错误");
			return true;
		}
		return false;
	}
	
}
