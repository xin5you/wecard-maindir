package com.cn.thinkx.wecard.api.module.withdraw.controller;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.common.redis.util.RedisPropertiesUtils;
import com.cn.thinkx.pms.base.domain.BaseReq;
import com.cn.thinkx.pms.base.domain.BaseResp;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.api.module.withdraw.service.WithdrawOrderDetailService;
import com.cn.thinkx.wecard.api.module.withdraw.service.WithdrawOrderService;
import com.cn.thinkx.wecard.api.module.withdraw.suning.utils.BizUtil;
import com.cn.thinkx.wecard.api.module.withdraw.suning.vo.BatchDataNotify;
import com.cn.thinkx.wecard.api.module.withdraw.suning.vo.Content;
import com.cn.thinkx.wecard.api.module.withdraw.vo.WelfaremartResellReq;
import com.cn.thinkx.wecard.api.module.withdraw.vo.WelfaremartResellResp;

/**
 * 知了企服用户提现控制类<br><br>
 * 
 * 功能 [批量代付至用户银行卡]<br>
 * 描述 [根据代付渠道区分请求路径，所有代付路径只支持POST请求方式，返回JSON格式数据]
 * 
 * @author pucker
 *
 */
@Controller
@RequestMapping("/withdraw")
public class WithdrawController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 固定线程池
	 */
	private static final ExecutorService es = Executors.newCachedThreadPool();

	@Autowired
	@Qualifier("withdrawOrderService")
	private WithdrawOrderService withdrawOrderService;
	
	@Autowired
	@Qualifier("withdrawOrderDetailService")
	private WithdrawOrderDetailService withdrawOrderDetailService;
	
	/**
	 * 易付宝批量代付
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/suning-yfb/withdraw", method = RequestMethod.POST)
	@ResponseBody
	public BaseResp yfbBatchWithdraw(@RequestBody BaseReq request) {
		// 返回结果
		BaseResp resp = new BaseResp();
		resp.setRespCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setRespMsg(BaseConstants.RESPONSE_EXCEPTION_INFO);
		
		// 获得易付宝代付请求参数
		String paramData = request.getParamData();
		if (StringUtil.isNullOrEmpty(paramData)) {
			logger.error("## 易付宝代付请求参数paramData为空");
			return resp;
		}
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		logger.info("获取知了企服代付定时job请求参数：{}", paramData);
		
		// 验签通过后才调用代付接口
		boolean isItTheSign = withdrawOrderService.YFBWithdrawCheckSign(paramData);
		if (!isItTheSign) {
			return resp;
		}
		
		String batchNo = BizUtil.generalBatchNo();// 随机生成唯一批次号
		String respStr = null;
		try {
			// 调用易付宝代付接口
			respStr = withdrawOrderService.YFBBatchWithdraw(batchNo, paramData);
		} catch (Exception e) {
			logger.error("## 调用易付宝代付接口异常{}", e);
		}
		
		if (respStr == null) {
			return resp;
		} else {
			JSONObject json = JSONObject.parseObject(respStr);
			String merchantNo = RedisPropertiesUtils.getProperty("WITHDRAW_YFB_MERCHANT_CODE");// 请求商户号
			String jsonKey = new StringBuffer().append(batchNo).append("_").append(merchantNo).toString();// 未全部受理成功key
			
			if (json.containsKey("responseCode") && "0000".equals(json.get("responseCode"))) {// 易付宝受理成功
				resp.setRespCode(BaseConstants.RESPONSE_SUCCESS_CODE);
				resp.setRespMsg(BaseConstants.RESPONSE_SUCCESS_INFO);
			} else if (json.containsKey(jsonKey)) {// 易付宝未全部受理成功
				JSONObject jsonValue = (JSONObject) json.get(jsonKey);
				String responseCode = jsonValue.getString("responseCode");
				String responseMsg = jsonValue.getString("responseMsg");
				resp.setRespCode(responseCode);
				resp.setRespMsg(responseMsg);
				logger.error("## 易付宝代付未全部受理成功 responseCode[{}] responseMsg[{}]", responseCode, responseMsg);
			} else {
				logger.error("## 解析易付宝返回异常 返回值：{}", respStr);
			}
		}
		
		sw.stop();
		logger.info("易付宝批量代付受理成功 总共耗时[{}ms]", sw.getTime());
		return resp;
	}
	
	/**
	 * 代付异步回调
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/suning-yfb/withdrawNotify")
	@ResponseBody
	public boolean welfareResellNotify(HttpServletRequest request) {
		String contentJson = request.getParameter("content");
		String sign = request.getParameter("sign");
		String sign_type = request.getParameter("sign_type");
		String vk_version = request.getParameter("vk_version");
		Content content = JSONArray.parseObject(contentJson, Content.class);
		
		BatchDataNotify batchData = new  BatchDataNotify();
		batchData.setContent(content);
		batchData.setSign(sign);
		batchData.setSign_type(sign_type);
		batchData.setVk_version(vk_version);
		
		logger.info("易付宝回调请求参数{}", JSONArray.toJSONString(batchData));
		//验证易付宝请求参数，更新出款订单表，新增出款订单明细表
		es.execute(new Runnable(){
			@Override
			public void run() {
				try {
					if (!StringUtil.isNullOrEmpty(batchData)) {
						boolean flag = BizUtil.verifySignature(contentJson, sign);
						if (flag) {
							withdrawOrderDetailService.YFBBatchWithdrawNotify(content);
						} else {
							logger.error("## 易付宝回调验签失败，待验签参数{}，易付宝签名{}", contentJson, sign);
						}
					}
				} catch (Exception e) {
					logger.error("## 易付宝回调异常{}", e);
				}
			}
		});
		//验证易付宝转让是否成功，成功则发送模板消息
		es.execute(new Runnable(){
			@Override
			public void run() {
				try {
					withdrawOrderDetailService.YFBBatchWithdrawSendMsg(content);
				} catch (Exception e) {
					logger.error("## 易付宝回调发送模板消息异常{}", e);
				}
			}
		});
		
		return true;
	}
	
	@RequestMapping(value = "/suning-yfb/withdrawQuery", method = RequestMethod.POST)
	@ResponseBody
	public String withdrawQuery(HttpServletRequest request) {
		String batchNo = request.getParameter("batchNo");
		String payMerchantNo = request.getParameter("payMerchantNo");
		String result = withdrawOrderService.YFBBatchWithdrawQuery(batchNo, payMerchantNo);
		logger.info("代付查询接口返回==========>[{}]", result);
		return result;
	}
	
	/**
	 * 卡券集市，转让接口
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/welfaremart/resellCommit", method = RequestMethod.POST)
	@ResponseBody
	public String welfaremartResellCommit(@RequestBody WelfaremartResellReq req) {
		logger.info("卡券集市--->转让接口，请求参数[{}]", JSONArray.toJSONString(req));
		WelfaremartResellResp resp = null;
		try {
			resp = withdrawOrderService.welfaremartResellCommit(req);
		} catch (Exception e) {
			logger.error("## 卡券集市--->转让接口，转让发生异常{}", e);
			return JSONArray.toJSONString(resp);
		}
		logger.info("卡券集市--->转让接口，返回参数[{}]", JSONArray.toJSONString(resp));
		return JSONArray.toJSONString(resp);
	}
	
}
