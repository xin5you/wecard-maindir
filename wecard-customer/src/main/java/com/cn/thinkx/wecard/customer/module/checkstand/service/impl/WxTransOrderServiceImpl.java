package com.cn.thinkx.wecard.customer.module.checkstand.service.impl;

import java.security.interfaces.RSAPrivateKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.beans.TxnPackageBean;
import com.cn.thinkx.common.activemq.service.WechatMQProducerService;
import com.cn.thinkx.common.redis.core.JedisClusterUtils;
import com.cn.thinkx.common.redis.util.RedisDictProperties;
import com.cn.thinkx.common.redis.util.SignUtil;
import com.cn.thinkx.common.redis.util.SignatureUtil;
import com.cn.thinkx.common.redis.util.TxnChannelSignatureUtil;
import com.cn.thinkx.common.wecard.domain.base.ResultHtml;
import com.cn.thinkx.common.wecard.domain.ecomorder.OrderInf;
import com.cn.thinkx.common.wecard.domain.merchant.MerchantInf;
import com.cn.thinkx.common.wecard.domain.merchant.MerchantManager;
import com.cn.thinkx.common.wecard.domain.shop.ShopInf;
import com.cn.thinkx.common.wecard.domain.trans.InterfaceTrans;
import com.cn.thinkx.common.wecard.domain.trans.TransLog;
import com.cn.thinkx.common.wecard.domain.trans.WxTransLog;
import com.cn.thinkx.common.wecard.domain.trans.WxTransOrder;
import com.cn.thinkx.common.wecard.domain.trans.WxTransOrderDetail;
import com.cn.thinkx.common.wecard.domain.user.UserInf;
import com.cn.thinkx.common.wecard.domain.user.UserMerchantAcct;
import com.cn.thinkx.common.wecard.module.trans.mapper.WxTransOrderMapper;
import com.cn.thinkx.facade.bean.CardBalQueryRequest;
import com.cn.thinkx.facade.service.HKBTxnFacade;
import com.cn.thinkx.pms.base.service.MessageService;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.RoleNameEnum;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.refundFalg;
import com.cn.thinkx.pms.base.utils.BaseConstants.templateMsgPayment;
import com.cn.thinkx.pms.base.utils.BaseConstants.templateMsgRefund;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.MD5Util;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.base.utils.RSAUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pms.base.utils.WechatCustomerMessageUtil;
import com.cn.thinkx.service.txn.Java2TxnBusinessFacade;
import com.cn.thinkx.wecard.customer.module.checkstand.service.WxTransOrderService;
import com.cn.thinkx.wecard.customer.module.checkstand.util.Constants;
import com.cn.thinkx.wecard.customer.module.checkstand.util.HKBTxnUtil;
import com.cn.thinkx.wecard.customer.module.checkstand.util.MessageUtil;
import com.cn.thinkx.wecard.customer.module.checkstand.util.OrderNotifyHttpClient;
import com.cn.thinkx.wecard.customer.module.checkstand.valid.WxTransOrderTxnReqValid;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.OrderBaseTxnResp;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.OrderRefund;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.TranslogRefundReq;
import com.cn.thinkx.wecard.customer.module.customer.service.CtrlSystemService;
import com.cn.thinkx.wecard.customer.module.customer.service.PersonInfService;
import com.cn.thinkx.wecard.customer.module.customer.service.UserInfService;
import com.cn.thinkx.wecard.customer.module.customer.service.UserMerchantAcctService;
import com.cn.thinkx.wecard.customer.module.customer.service.WxTransLogService;
import com.cn.thinkx.wecard.customer.module.merchant.service.MerchantInfService;
import com.cn.thinkx.wecard.customer.module.merchant.service.MerchantManagerService;
import com.cn.thinkx.wecard.customer.module.merchant.service.ShopInfService;
import com.cn.thinkx.wecard.customer.module.merchant.service.TransLogService;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;
import com.cn.thinkx.wecard.customer.module.pub.service.OrderInfService;
import com.cn.thinkx.wecard.customer.module.wxcms.WxCmsContents;
import com.cn.thinkx.wecard.customer.module.wxcms.service.AccountService;
import com.cn.thinkx.wechat.base.wxapi.domain.Account;
import com.cn.thinkx.wechat.base.wxapi.process.CustomerWxPayApi;
import com.cn.thinkx.wechat.base.wxapi.process.CustomerWxPayApiClient;
import com.cn.thinkx.wechat.base.wxapi.process.MpAccount;
import com.cn.thinkx.wechat.base.wxapi.process.MsgXmlUtil;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;
import com.cn.thinkx.wechat.base.wxapi.util.WXTemplateUtil;
import com.cn.thinkx.wechat.base.wxapi.util.WxConstants;
import com.cn.thinkx.wechat.base.wxapi.vo.WxPayCallback;

import net.sf.json.JSONObject;

@Service("wxTransOrderService")
public class WxTransOrderServiceImpl implements WxTransOrderService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("wxTransOrderMapper")
	private WxTransOrderMapper wxTransOrderMapper;

	@Autowired
	@Qualifier("wxTransLogService")
	private WxTransLogService wxTransLogService;

	@Autowired
	@Qualifier("merchantInfService")
	private MerchantInfService merchantInfService;

	@Autowired
	@Qualifier("userMerchantAcctService")
	private UserMerchantAcctService userMerchantAcctService;

	@Autowired
	@Qualifier("userInfService")
	private UserInfService userInfService;

	@Autowired
	@Qualifier("java2TxnBusinessFacade")
	private Java2TxnBusinessFacade java2TxnBusinessFacade;

	@Autowired
	@Qualifier("shopInfService")
	private ShopInfService shopInfService;

	@Autowired
	@Qualifier("merchantManagerService")
	private MerchantManagerService merchantManagerService;

	@Autowired
	@Qualifier("wechatMQProducerService")
	private WechatMQProducerService wechatMQProducerService;

	@Autowired
	@Qualifier("personInfService")
	private PersonInfService personInfService;

	@Autowired
	@Qualifier("ctrlSystemService")
	private CtrlSystemService ctrlSystemService;

	@Autowired
	private HKBTxnFacade hkbTxnFacade;

	@Autowired
	@Qualifier("transLogService")
	private TransLogService transLogService;

	@Autowired
	@Qualifier("accountService")
	private AccountService accountService;

	@Autowired
	@Qualifier("messageService")
	private MessageService messageService;
	
	@Autowired
	@Qualifier("orderInfService")
	private OrderInfService orderInfService;

	/**
	 * 根据订单号查询
	 * 
	 * @param orderKey
	 * @return
	 */
	public WxTransOrder getWxTransOrdeByOrderKey(String orderKey) {
		return wxTransOrderMapper.getWxTransOrdeByOrderKey(orderKey);
	}

	/**
	 * 根据流水号查询
	 * 
	 * @param channelCode
	 *            渠道号
	 * @param orderId
	 *            商户订单号
	 * @param txnFlowNo
	 *            交易流水号
	 * @return
	 */
	public WxTransOrder getWxTransOrdeByTxnFlowNo(String channelCode, String orderId, String txnFlowNo) {
		return wxTransOrderMapper.getWxTransOrdeByTxnFlowNo(channelCode, orderId, txnFlowNo);
	}

	/**
	 * 新增交易订单
	 * 
	 * @param transOrder
	 * @return
	 */
	public int insertWxTransOrder(WxTransOrder transOrder) {
		return wxTransOrderMapper.insertWxTransOrder(transOrder);
	}

	/**
	 * 保存订单 订单明细
	 * 
	 * @param transOrder
	 * @param orderDetail
	 * @return
	 */
	public int saveWxTransOrder(WxTransOrder transOrder, WxTransOrderDetail orderDetail) {
		int operNum = 0;
		String orderKey = wxTransLogService.getPrimaryKey();
		transOrder.setOrderKey(orderKey);
		operNum = wxTransOrderMapper.insertWxTransOrder(transOrder);

		if (operNum > 0) {
			// insert 订单明细
			String orderDetailId = wxTransLogService.getPrimaryKey();
			orderDetail.setOrderListId(orderDetailId);
			orderDetail.setOrderKey(orderKey);
			if (StringUtil.isNullOrEmpty(orderDetail.getSignType())) {
				orderDetail.setSignType("MD5");
			}
			if (StringUtil.isNullOrEmpty(orderDetail.getNotifyUrl())) { // 如果通知NotifyType
																		// 0:通知；1：不通知
				orderDetail.setNotifyType("0");
			} else {
				orderDetail.setNotifyType("1");
			}
			operNum = wxTransOrderMapper.insertWxTransOrderDetail(orderDetail);
		}
		return operNum;
	}

	/**
	 * 修改交易订单
	 * 
	 * @param transOrder
	 * @return
	 */
	public int updateWxTransOrder(WxTransOrder transOrder) {
		return wxTransOrderMapper.updateWxTransOrder(transOrder);
	}

	/**
	 * 根据订单号查找订单明细
	 * 
	 * @param orderKey
	 * @return
	 */
	public WxTransOrderDetail getWxTransOrdeDetailByOrderId(String orderId) {
		return wxTransOrderMapper.getWxTransOrdeDetailByOrderId(orderId);
	}

	/**
	 * 新增交易订单明细
	 * 
	 * @param transOrder
	 * @return
	 */
	public int insertWxTransOrderDetail(WxTransOrderDetail orderDetail) {
		return wxTransOrderMapper.insertWxTransOrderDetail(orderDetail);
	}

	/**
	 * 修改交易订单明细
	 * 
	 * @param transOrder
	 * @return
	 */
	public int updateWxTransOrderDetail(WxTransOrderDetail orderDetail) {

		return wxTransOrderMapper.updateWxTransOrderDetail(orderDetail);
	}

	/** 收银台 消费交易-插入微信端流水 **/
	public WxTransLog insertWxTransLogByTransOrder(HttpServletRequest request) {
		WxTransLog log = new WxTransLog();

		String sponsor = request.getParameter("sponsor");
		String payType = request.getParameter("payType");
		String orderKey = request.getParameter("orderKey");
		
		/** 查询微信端已有成功订单数量 */
		int orderNum = wxTransLogService.countWxTransLogByOrderId(orderKey);
		if (orderNum > 0) {
			logger.error("## 收银台会员卡消费 orderKey[{}]：该订单号已有成功流水记录", orderKey);
			log.setOrderNum(orderNum);
			return log;
		}

		/** 查找订单信息 **/
		WxTransOrder transOrder = this.getWxTransOrdeByOrderKey(orderKey);
		if (transOrder == null)
			return null;
		
		CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
		if (cs != null) {
			if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入微信端流水
				String id = wxTransLogService.getPrimaryKey();
				log.setWxPrimaryKey(id);
				log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
				log.setSettleDate(cs.getSettleDate());// 交易日期
				if (StringUtil.isNullOrEmpty(payType) || "VIPCARD_PAY".equals(payType)) {
					log.setTransId(TransCode.CW10.getCode());// 交易类型 会员卡支付
				} else {
					log.setTransId(TransCode.CW71.getCode());// 交易类型 微信快捷支付
				}
				log.setTransSt(0);// 插入时为0，报文返回时更新为1
				log.setInsCode(transOrder.getInsCode());// 客户端传过来的机构code
				log.setMchntCode(transOrder.getMchntCode());
				log.setShopCode(transOrder.getShopCode());
				log.setSponsor(sponsor);
				log.setOperatorOpenId(transOrder.getUserInfUserName());
				log.setTransChnl(transOrder.getTransChnl()); // 交易渠道
				log.setUserInfUserName(transOrder.getUserInfUserName());
				log.setTransAmt(transOrder.getOrderAmt());// 实际交易金额
															// 插入时候默认与上送金额一致
				log.setUploadAmt(transOrder.getOrderAmt());// 上送金额
				log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
				log.setOrderId(transOrder.getOrderKey());
				int i = wxTransLogService.insertWxTransLog(log);// 插入流水记录
				if (i != 1) {
					logger.info("消费交易--->insertIntfaceTransLog微信端插入流水记录数量不为1");
					return null;
				}

				WxTransOrderDetail orderDetail = transOrder.getOrderDetail();

				orderDetail.setTransId(log.getTransId());
				orderDetail.setTransSt(String.valueOf(log.getTransSt()));
				this.updateWxTransOrderDetail(orderDetail);

			} else {
				logger.info("消费交易--->日切信息交易允许状态：日切中");
				return null;
			}
		}
		return log;
	}

	/** 收银台 会员卡消费交易-验密后调用交易核心 **/
	public TxnResp doTransOrderJava2TxnBusiness(HttpServletRequest request) {
		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();

		TxnResp resp = new TxnResp();
		String wxPrimaryKey = request.getParameter("wxPrimaryKey");
		String tableNum = request.getParameter("tableNum");
		String pinTxn = request.getParameter("pinTxn");
		String orderKey = request.getParameter("orderKey");

		/** 查找订单信息 **/
		WxTransOrder transOrder = getWxTransOrdeByOrderKey(orderKey);

		if (transOrder == null) {
			logger.error("## 收银台会员卡消费 orderKey[{}]：找不到流水记录", orderKey);
			resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
			resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
			return resp;
		}
		
		/** 查询微信端已有成功订单数量 */
		int orderNum = wxTransLogService.countWxTransLogByOrderId(orderKey);
		if (orderNum > 0) {
			logger.error("## 收银台会员卡消费 orderKey[{}]：该订单号已有成功流水记录", orderKey);
			resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
			resp.setInfo("订单已经处理成功");
			return resp;
		}

		/** 查询微信端交易流水 */
		WxTransLog wxTransLog = wxTransLogService.getWxTransLogById(wxPrimaryKey);

		if (wxTransLog == null) {
			logger.error("## 收银台会员卡 wxPrimaryKey[{}]：没查询到交易流水", wxPrimaryKey);
			resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
			resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
			return resp;
		}
		wxTransLog.setTableNum(tableNum);

		if (!transOrder.getOrderKey().equals(wxTransLog.getOrderId())) {
			logger.error("## wxp交易流水订单号与wx_trans_order订单号不匹配{[]}", wxPrimaryKey);
			resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
			resp.setInfo("交易订单不匹配");
			return resp;
		}
		
		CtrlSystem cs = null;
		try {
			cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (cs != null) {
			TxnPackageBean txnBean = new TxnPackageBean();
			txnBean.setTxnType(wxTransLog.getTransId() + "0");// 交易类型，发送报文时补0
			txnBean.setSwtTxnDate(DateUtil.getCurrentDateStr());// 交易日期
			txnBean.setSwtTxnTime(DateUtil.getCurrentTimeStr());// 交易时间
			txnBean.setSwtSettleDate(cs.getSettleDate());// 清算日期
			txnBean.setSwtFlowNo(wxPrimaryKey);
			txnBean.setIssChannel(wxTransLog.getInsCode());// 机构号
			txnBean.setInnerMerchantNo(wxTransLog.getMchntCode());// 商户号
			txnBean.setInnerShopNo(wxTransLog.getShopCode());// 门店号
			txnBean.setTxnAmount(wxTransLog.getTransAmt());// 交易金额
			txnBean.setOriTxnAmount(wxTransLog.getUploadAmt());// 原交易金额
			txnBean.setChannel(wxTransLog.getTransChnl());
			txnBean.setCardNo("U" + wxTransLog.getUserInfUserName());// 卡号
																		// U开头为客户端交易，C开头则为刷卡交易

			try {
				if (!StringUtil.isNullOrEmpty(pinTxn)) {
					RSAPrivateKey privateKey = (RSAPrivateKey) request.getSession()
							.getAttribute(WxConstants.TRANS_ORDER_RSA_PRIVATE_KEY_SESSION);
					String descrypedPwd = RSAUtil.decryptByPrivateKey(pinTxn, privateKey);// 解密后的密码,pinTxn是提交过来的密码
					txnBean.setPinTxn(descrypedPwd);// 交易密码
				} else {
					txnBean.setPinTxn("");// 交易密码
				}

			} catch (Exception e1) {
				logger.error("收银台会员卡消费交易 解密交易密码出错", e1);
				resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
				resp.setInfo("解密交易密码出错");
				wxTransLog.setTransSt(1);// 插入时为0，报文返回时更新为1
				wxTransLogService.updateWxTransLog(wxTransLog, resp);
				return resp;
			}
			txnBean.setChannel(transOrder.getTransChnl());// 渠道号
			txnBean.setUserId(transOrder.getUserId());// 用户id
			txnBean.setTimestamp(System.currentTimeMillis());// 时间戳
			String signature = TxnChannelSignatureUtil.genSign(txnBean); // 生成的签名
			txnBean.setSignature(signature);

			// 远程调用消费接口
			String json = new String();
			try {
				json = java2TxnBusinessFacade.consumeTransactionITF(txnBean);
				resp = JSONArray.parseObject(json, TxnResp.class);
				if (resp == null || !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
					json = java2TxnBusinessFacade.transExceptionQueryITF(wxPrimaryKey);
					resp = JSONArray.parseObject(json, TxnResp.class);
				}

			} catch (Exception e) {
				logger.error("远程调用消费接口异常" + json + "流水号：" + wxPrimaryKey, e);
			}

			if (resp == null) {
				resp = new TxnResp();
				resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
				resp.setInfo("对不起，交易出现异常，请联系客服人员人工处理");
			}

			// 更新微信交易流水
			try {
				wxTransLog.setTransSt(1);// 插入时为0，报文返回时更新为1
				wxTransLog.setRespCode(resp.getCode());
				wxTransLogService.updateWxTransLog(wxTransLog, resp);
			} catch (Exception e) {
				logger.error("收银台updateWxTransLog异常", e);
			}

			WxTransOrderDetail orderDetail = null;
			// 更新交易订单流水
			try {
				orderDetail = transOrder.getOrderDetail();
				orderDetail.setRespCode(resp.getCode());
				orderDetail.setSettleDate(wxTransLog.getSettleDate());// 日切时间
				orderDetail.setTransSt("1");
				if (BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
					orderDetail.setTxnFlowNo(resp.getInterfacePrimaryKey());
				}
				updateWxTransOrderDetail(orderDetail);

			} catch (Exception e) {
				logger.error("收银台updateWxTransOrder异常", e);
			}
			if (BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
				// 发送客服消息
				try {
					String payAmt = NumberUtils.RMBCentToYuan(wxTransLog.getTransAmt());
					ShopInf shopInf = shopInfService.getShopInfByCode(wxTransLog.getShopCode());
					String txnDate = DateUtil.getChineseDateFormStr(txnBean.getSwtTxnDate() + txnBean.getSwtTxnTime());
					CardBalQueryRequest req = new CardBalQueryRequest();
					req.setChannel(ChannelCode.CHANNEL1.toString());
					req.setUserId(wxTransLog.getOperatorOpenId());
					req.setInnerMerchantNo(wxTransLog.getMchntCode());
					req.setTimestamp(System.currentTimeMillis());
					req.setSign(SignUtil.genSign(req));
					String cardBalJson = hkbTxnFacade.cardBalanceQueryITF(req);
					TxnResp cbResp = JSONArray.parseObject(cardBalJson, TxnResp.class);
					String accBal = StringUtil.isNullOrEmpty(cbResp.getBalance()) ? "0" : cbResp.getBalance();

					/**
					 * 客服消息-客户 String
					 * notice_c=String.format(WechatCustomerMessageUtil.
					 * WECHAT_CUSTOMER_CW10_SUCCESS,txnDate,shopInf.getMchntName
					 * (),shopInf.getShopName(),payAmt);
					 * wechatMQProducerService.sendWechatMessage(mpAccount.
					 * getAccount(), notice_c, wxTransLog.getOperatorOpenId());
					 */
					String channelName = shopInf.getShopName();
					if (ChannelCode.CHANNEL6.toString().equals(transOrder.getTransChnl())) {
						OrderInf orderInf = orderInfService.getOrderInfById(transOrder.getDmsRelatedKey());
						if (orderInf != null) {
							channelName = templateMsgPayment.findByCode(orderInf.getChannel()).getValue();
						}
					} else if (ChannelCode.CHANNEL8.toString().equals(transOrder.getTransChnl())) {
						channelName = templateMsgPayment.findByCode(transOrder.getTransChnl()).getValue();
					} else if (ChannelCode.CHANNEL9.toString().equals(transOrder.getTransChnl())) {
						channelName = templateMsgPayment.findByCode(transOrder.getTransChnl()).getValue();
					}
					
					/** 模板消息-客户 */
					wechatMQProducerService.sendTemplateMsg(mpAccount.getAccount(), wxTransLog.getOperatorOpenId(),
							"WX_TEMPLATE_ID_0", null, WXTemplateUtil.setHKBPayData(txnDate, shopInf.getMchntName(),
									shopInf.getShopName(), payAmt, NumberUtils.RMBCentToYuan(accBal), channelName));

					/**
					 * =======================收款通知 发送B端管理员
					 * ======================
					 **/
					String customerPhone = personInfService.getPhoneNumberByOpenId(wxTransLog.getOperatorOpenId());
					/** 发送客服消息
					String notice_m = String.format(WechatCustomerMessageUtil.WECHAT_MCHNT_CW10_SUCCESS,
							shopInf.getMchntName(), StringUtil.getPhoneNumberFormatLast4(customerPhone),
							shopInf.getShopName(), payAmt, txnDate, resp.getInterfacePrimaryKey());*/

					List<MerchantManager> mngList = merchantManagerService.getMerchantManagerByRoleType(
							wxTransLog.getMchntCode(), wxTransLog.getShopCode(),
							RoleNameEnum.CASHIER_ROLE_MCHANT.getRoleType());
					if (mngList != null && mngList.size() > 0) {
						String mchntAcount = RedisDictProperties.getInstance().getdictValueByCode("WX_MCHNT_ACCOUNT");
						String payType = "会员卡付款";
						for (MerchantManager mng : mngList) {
							/** 	发送客服消息
							wechatMQProducerService.sendWechatMessage(mchntAcount, notice_m, mng.getMangerName());*/
							/**	发送模板消息（收款通知）*/
							wechatMQProducerService.sendTemplateMsg(mchntAcount, mng.getMangerName(), "WX_TEMPLATE_ID_6", null, 
									WXTemplateUtil.setProceedsMsg(StringUtil.getPhoneNumberFormatLast4(customerPhone), shopInf.getMchntName(), NumberUtils.RMBCentToYuan(payAmt), shopInf.getShopName(), payType, resp.getInterfacePrimaryKey(), txnDate));
						}
					}
				} catch (Exception e) {
					logger.error("发送客服消息接口异常--------》 接口流水号：" + wxPrimaryKey, e);
				}
				// 异步通知
				try {
					final WxTransOrder tsOrder = transOrder;

					Thread rechargeThread = new Thread(new Runnable() {
						public void run() {
							String respResult = new OrderNotifyHttpClient().doTransOrderNotifyRequest(tsOrder);
							WxTransOrderDetail orderDetail = tsOrder.getOrderDetail();
							orderDetail.setOrderRespStat(respResult);
							updateWxTransOrderDetail(orderDetail);// 更新订单明细 通知状态
						};
					});
					rechargeThread.start();
				} catch (Exception e) {
					logger.error("异步通知异常--------》", e);
				}
			} else {
				resp.setTransAmt(wxTransLog.getTransAmt());// 实际转换后的金额 单位 元
				return resp;
			}

		} else {
			logger.info("消费交易--->日切信息交易允许状态：日切中");
			return null;
		}
		return resp;
	}

	/** 收银台 微信快捷支付 回调通知 **/
	public String doTransOrderWeChantQuickNotify(HttpServletRequest request) {

		WxPayCallback back = new WxPayCallback();
		back.setReturn_code("FAIL");// 返回code默认失败
		back.setReturn_msg("快捷支付交易失败");// 返回msg默认业务失败
		JSONObject obj = null;
		try {
			obj = CustomerWxPayApi.parseWxPayReturnXml(request);
		} catch (Exception e) {
			logger.error("快捷支付交易--->解析微信支付返回信息失败", e);
			return MsgXmlUtil.toXML(back);
		}

		String wxPrimaryKey = StringUtil.trim(obj.getString("attach"));// 业务流水(微信端)
		CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
		if (cs == null) {
			logger.info("快捷支付交易返回通知--->日切信息查询失败");
		}
		WxTransLog log = wxTransLogService.getWxTransLogById(wxPrimaryKey);// 查询业务流水

		TxnPackageBean txnBean = new TxnPackageBean();
		if (log != null) {
			log.setTableNum(cs.getCurLogNum());
			// 支付交易结果-成功
			if (obj.containsKey("return_code") && "SUCCESS".equals(obj.getString("return_code"))) {
				// 业务结果-成功
				if (obj.containsKey("result_code") && "SUCCESS".equals(obj.getString("result_code"))) {
					// 业务数据交易状态为0时表示微信支付通知未处理，此时处理业务逻辑
					if (log.getTransSt() == 0) {
						String cashFee = obj.getString("cash_fee");// 现金支付金额
						String openid = log.getUserInfUserName();

						int transAmt = Integer.parseInt(log.getTransAmt());// 获取实际订单金额
						String transId = obj.getString("transaction_id");// 微信支付订单号

						String notifySign = CustomerWxPayApi.getPayNotifySign(obj);// 生成通知签名
						// 验签通过
						if (obj.containsKey("sign") && obj.getString("sign").equals(notifySign)) {

							UserInf userInf = userInfService.getUserInfByOpenId(openid);

							txnBean.setTxnType(log.getTransId() + "0");// 交易类型，发送报文时补0
							txnBean.setSwtTxnDate(DateUtil.getCurrentDateStr());// 交易日期
							txnBean.setSwtTxnTime(DateUtil.getCurrentTimeStr());// 交易时间
							txnBean.setSwtSettleDate(log.getSettleDate());// 清算日期
							txnBean.setSwtFlowNo(log.getWxPrimaryKey());
							txnBean.setIssChannel(log.getInsCode());// 机构号，从机构信息表中获取
							txnBean.setInnerMerchantNo(log.getMchntCode());// 商户号
							txnBean.setInnerShopNo(log.getShopCode());// 门店号
							txnBean.setTxnAmount(String.valueOf(transAmt));// 交易金额
							txnBean.setOriTxnAmount(log.getUploadAmt());// 原交易金额
							txnBean.setChannel(log.getTransChnl());// 渠道号
							txnBean.setUserId(userInf.getUserId());// 用户id
							txnBean.setCardNo("U" + openid);
							txnBean.setTimestamp(System.currentTimeMillis());// 时间戳
							String signature = TxnChannelSignatureUtil.genSign(txnBean); // 生成的签名
							txnBean.setSignature(signature);

							String json = new String();
							// 远程调用消费接口
							TxnResp resp = new TxnResp();
							try {
								json = java2TxnBusinessFacade.quickConsumeTransactionITF(txnBean);// 远程调用快捷消费接口
								resp = JSONArray.parseObject(json, TxnResp.class);

								/*** 远程调用接口异常处理 ***/
								if (resp == null) {
									resp = new TxnResp();
								}
								if (!BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
									json = java2TxnBusinessFacade.transExceptionQueryITF(log.getWxPrimaryKey());
									resp = JSONArray.parseObject(json, TxnResp.class);
								}

							} catch (Exception e) {
								logger.error("快捷支付交易返回通知--->远程调用快捷支付接口异常，流水号：" + wxPrimaryKey, e);
							}

							if (resp == null) {
								resp = new TxnResp();
								resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
								resp.setInfo("对不起，交易出现异常，请联系客服人员人工处理");
							}
							// 更新微信端流水
							try {
								log.setDmsRelatedKey(transId);// 微信流水号
								log.setRespCode(resp.getCode());// 返回码
								wxTransLogService.updateWxTransLog(log, resp);// 更新微信端流水
							} catch (Exception e) {
								logger.error("快捷支付交易返回通知--->更新微信端流水异常，流水号：" + wxPrimaryKey, e);
							}
							/** 查找订单信息 **/
							WxTransOrder transOrder = this.getWxTransOrdeByOrderKey(log.getOrderId());

							// 更新交易订单流水
							try {
								logger.info("resp  is -------------->" + JSONArray.toJSONString(resp));
								transOrder.getOrderDetail().setRespCode(resp.getCode());
								transOrder.getOrderDetail().setTransSt("1");
								transOrder.getOrderDetail().setSettleDate(log.getSettleDate());
								if (BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
									transOrder.getOrderDetail().setTxnFlowNo(resp.getInterfacePrimaryKey());
								}
								this.updateWxTransOrderDetail(transOrder.getOrderDetail());
							} catch (Exception e) {
								logger.error("快捷支付交易返回通知--->更新微信交易流水异常，流水号：" + wxPrimaryKey, e);
							}

							MpAccount c_mpAccount = WxMemoryCacheClient.getMpAccount(WxCmsContents.getCurrMpAccount());
							String payAmt = NumberUtils.RMBCentToYuan(transAmt);// 实际支付金额
							ShopInf shopInf = shopInfService.getShopInfByCode(log.getShopCode()); // 所属门店
							// 业务逻辑处理成功 发送客服消息
							if (BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
								try {
									String txnDate = DateUtil
											.getChineseDateFormStr(txnBean.getSwtTxnDate() + txnBean.getSwtTxnTime());
									/**
									 * 客服消息-客户 String notice_c=String.format(
									 * WechatCustomerMessageUtil.
									 * WECHAT_CUSTOMER_W71_SUCCESS,txnDate,
									 * shopInf.getMchntName(),shopInf.
									 * getShopName(),payAmt);
									 * wechatMQProducerService.sendWechatMessage
									 * (c_mpAccount.getAccount(), notice_c,
									 * openid);
									 */
									/** 发送模板消息-客户 */
									wechatMQProducerService.sendTemplateMsg(c_mpAccount.getAccount(), openid,
											"WX_TEMPLATE_ID_1", null, WXTemplateUtil.setQuickPayData(txnDate,
													shopInf.getMchntName(), shopInf.getShopName(), payAmt));

									/**
									 * =======================收款通知 发送B端管理员
									 * ======================
									 **/
									String customerPhone = personInfService.getPhoneNumberByOpenId(openid);
									/** 发送客服消息
									String notice_m = String.format(WechatCustomerMessageUtil.WECHAT_MCHNT_W71_SUCCESS,
											shopInf.getMchntName(), StringUtil.getPhoneNumberFormatLast4(customerPhone),
											shopInf.getShopName(), NumberUtils.RMBCentToYuan(transAmt), txnDate,
											resp.getInterfacePrimaryKey());*/

									List<MerchantManager> mngList = merchantManagerService.getMerchantManagerByRoleType(
											log.getMchntCode(), log.getShopCode(),
											RoleNameEnum.CASHIER_ROLE_MCHANT.getRoleType());
									if (mngList != null && mngList.size() > 0) {
										String mchntAcount = RedisDictProperties.getInstance()
												.getdictValueByCode("WX_MCHNT_ACCOUNT");
										String payType = "微信支付";
										for (MerchantManager mng : mngList) {
											/** 发送客服消息
											wechatMQProducerService.sendWechatMessage(mchntAcount, notice_m,
													mng.getMangerName());*/
											/**发送模板消息（收款通知）*/
											wechatMQProducerService.sendTemplateMsg(mchntAcount, mng.getMangerName(), "WX_TEMPLATE_ID_6", null, 
													WXTemplateUtil.setProceedsMsg(StringUtil.getPhoneNumberFormatLast4(customerPhone), shopInf.getMchntName(), NumberUtils.RMBCentToYuan(transAmt), shopInf.getShopName(), payType, resp.getInterfacePrimaryKey(), txnDate));
										}
									}
								} catch (Exception e) {
									logger.error("快捷支付交易返回通知--->发送客服消息异常，流水号：" + wxPrimaryKey, e);
								}
								// 异步通知
								try {
									final WxTransOrder tsOrder = transOrder;
									Thread rechargeThread = new Thread(new Runnable() {
										public void run() {
											String respResult = new OrderNotifyHttpClient()
													.doTransOrderNotifyRequest(tsOrder);
											tsOrder.getOrderDetail().setOrderRespStat(respResult);
											updateWxTransOrderDetail(tsOrder.getOrderDetail());
										};
									});
									rechargeThread.start();
								} catch (Exception e) {
									logger.error("异步通知异常--------》", e);
								}

							} else { // 业务逻辑处理失败，进行退款
								try {
									String txnDate = DateUtil
											.getChineseDateFormStr(txnBean.getSwtTxnDate() + txnBean.getSwtTxnTime());
									json = java2TxnBusinessFacade.transExceptionQueryITF(log.getWxPrimaryKey());
									resp = JSONArray.parseObject(json, TxnResp.class);
									if (!BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
										// 请求微信退款 暂时只做退款请求 不做其他业务处理
										if (cashFee != null) {
											log.setTransAmt(cashFee);
										}
										// 微信退款
										JSONObject refundResJson = wxTransRefundOrder(log, request);
										if (refundResJson != null) {
											if (refundResJson.containsKey("return_code")
													&& "SUCCESS".equals(refundResJson.getString("return_code"))) {
												/*
												 * String
												 * notice_c=String.format(
												 * WechatCustomerMessageUtil.
												 * WECHAT_CUSTOMER_W71_REFUNDORDER_SUCCESS
												 * ,txnDate,shopInf.getMchntName
												 * (),shopInf.getShopName(),
												 * payAmt);
												 * wechatMQProducerService.
												 * sendWechatMessage(c_mpAccount
												 * .getAccount(), notice_c,
												 * openid);
												 */
												/** 发送模板消息-客户 */
												wechatMQProducerService.sendTemplateMsg(c_mpAccount.getAccount(),
														openid, "WX_TEMPLATE_ID_2", null,
														WXTemplateUtil.setPayBackData(txnDate, payAmt));
											} else {
												String notice_c = String.format(
														WechatCustomerMessageUtil.WECHAT_CUSTOMER_W71_REFUNDORDER_FAIL,
														log.getWxPrimaryKey());
												wechatMQProducerService.sendWechatMessage(c_mpAccount.getAccount(),
														notice_c, openid);
											}

										}
									}
								} catch (Exception e) {
									logger.error("快捷支付交易返回通知--->用户退款异常，流水号：" + wxPrimaryKey, e);
								}
							}
						} else {
							back.setReturn_msg("签名失败");
							logger.info("快捷支付交易返回通知--->验签失败，签名参数为：" + obj);

							// 微信退款
//							JSONObject refundResJson = wxTransRefundOrder(log, request);
							wxTransLogService.updateWxTransLog(log, null);// 更新微信端流水
							back.setReturn_code("SUCCESS");
							back.setReturn_msg("OK");
							return MsgXmlUtil.toXML(back);

						}
					} else {// 业务数据交易状态不为0时(通常为1)表示支付通知已处理，此时直接给微信通知返回成功
						if (BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(log.getRespCode())) {
							back.setReturn_code("SUCCESS");
							back.setReturn_msg("OK");
						}
						return MsgXmlUtil.toXML(back);
					}
				} else {
					logger.info("快捷支付交易返回通知--->微信支付业务失败：" + obj.getString("err_code_des"));
				}
			} else {
				logger.info("快捷支付交易返回通知--->微信支付交易失败：" + obj.getString("return_msg"));
				back.setReturn_msg(obj.getString("return_msg"));
			}
		} else {
			logger.info("快捷支付交易返回通知--->查询业务流水为空，流水号为：" + wxPrimaryKey);
		}
		return MsgXmlUtil.toXML(back);
	}

	/***
	 * 微信快捷消费退款
	 * 
	 * @param wxTransLog
	 * @param request
	 * @return
	 */
	private JSONObject wxTransRefundOrder(WxTransLog wxTransLog, HttpServletRequest request) {
		JSONObject refundResJson = CustomerWxPayApiClient.getRefundOrder(WxMemoryCacheClient.getSingleMpAccount(),
				wxTransLog.getWxPrimaryKey(), wxTransLog.getWxPrimaryKey(), wxTransLog.getTransAmt(),
				wxTransLog.getTransAmt(), request);// 申請退款
		if (refundResJson != null) {
			if (refundResJson.containsKey("return_code") && "SUCCESS".equals(refundResJson.getString("return_code"))) {
				return refundResJson;
			} else {
				// 如果失败 重新发起退款
				refundResJson = CustomerWxPayApiClient.getRefundOrder(WxMemoryCacheClient.getSingleMpAccount(),
						wxTransLog.getWxPrimaryKey(), wxTransLog.getWxPrimaryKey(), wxTransLog.getTransAmt(),
						wxTransLog.getTransAmt(), request);// 申請退款
			}
		}
		return refundResJson;
	}

	/*** 判断用户是否已经是某商户会员 **/
	public String getMchntAccBal(String innerMerchantNo, String userId) {
		MerchantInf merchantInf = merchantInfService.getMerchantInfByCode(innerMerchantNo);
		String userMchntAccBal = "0";
		if (merchantInf != null) {
			UserMerchantAcct userMerchantAcct = new UserMerchantAcct();
			userMerchantAcct.setMchntCode(innerMerchantNo);
			userMerchantAcct.setUserId(userId);
			List<UserMerchantAcct> productlist = userMerchantAcctService.getUserMerchantAcctByUser(userMerchantAcct);
			if (productlist != null && productlist.size() > 0) {
				userMchntAccBal = productlist.get(0).getAccBal();
			}
		}
		return userMchntAccBal;
	}

	@Override
	public ResultHtml doTranslogRefund(TranslogRefundReq translogRefund, HttpServletRequest request) {
		ResultHtml resultHtml = new ResultHtml();
		resultHtml.setStatus(false);
		resultHtml.setMsg(Constants.TransRespEnum.ERROR.getName());
		OrderBaseTxnResp txnResp = new OrderBaseTxnResp();
		// 参数校验
		try {
			/*** 参数校验 **/
			if (WxTransOrderTxnReqValid.translogRefundValid(translogRefund, txnResp)) {
				resultHtml.setMsg(txnResp.getInfo());
				return resultHtml;
			}

			/** step2. 动态口令验证 **/
			if (StringUtil.isNullOrEmpty(translogRefund.getPhoneNumber())) {
				resultHtml.setMsg(MessageUtil.ERROR_MESSAGE_PHONE_CODE);// 手机号验证码
				return resultHtml;
			}
			if (StringUtil.isNullOrEmpty(translogRefund.getPhoneNumber())
					|| !checkPhoneCode(translogRefund.getPhoneCode(), resultHtml)) {
				resultHtml.setStatus(false);
				return resultHtml;
			}
		} catch (Exception ex) {
			resultHtml.setStatus(false);
			resultHtml.setMsg(Constants.TransRespEnum.ERROR.getName());
			logger.error("申请退款--->参数校验校验失败", ex);
		}

		TransLog transLog = transLogService.getTransLogByRelatedKey(translogRefund.getTxnPrimaryKey());

		if (transLog == null) {
			resultHtml.setStatus(false);
			resultHtml.setMsg("当前交易订单号不存在，请重新确认");
			return resultHtml;
		}

		CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
		if (cs == null || !Constants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {
			resultHtml.setStatus(false);
			resultHtml.setMsg("网络连接失败，请稍后再试");
			logger.info("申请退款--->日切信息交易允许状态：日切中");
			return resultHtml;
		}

		TxnResp payBackResp = new TxnResp();// 外部退款，Resp 对象
		InterfaceTrans itfLog = transLogService.getInterfaceTransByPrimaryKey(transLog.getDmsRelatedKey());
		if (itfLog == null) {
			resultHtml.setStatus(false);
			resultHtml.setMsg("订单流水号 " + transLog.getDmsRelatedKey() + " 不存在");
			logger.info("申请退款--->ITF订单号：[{}]ITF流水不存在", transLog.getDmsRelatedKey());
			return resultHtml;
		}

		// 记录流水
		WxTransLog log = new WxTransLog();
		String dms_related_key = "";// 外部系统交易流水号
		try {
			String id = wxTransLogService.getPrimaryKey();
			log.setWxPrimaryKey(id);
			log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
			log.setSettleDate(cs.getSettleDate());// 交易日期
			if (TransCode.CW71.getCode().equals(itfLog.getTransId())) {
				log.setTransId(TransCode.CW74.getCode());// 交易类型
			} else {
				log.setTransId(TransCode.CW11.getCode());// 交易类型
			}
			log.setTransSt(0);// 插入时为0，报文返回时更新为1
			log.setInsCode(transLog.getInsCode());
			log.setMchntCode(transLog.getMchntCode());
			log.setShopCode(transLog.getShopCode());
			// log.setOperatorOpenId(openid);

			log.setTransChnl(ChannelCode.CHANNEL1.toString());
			log.setUserInfUserName(transLog.getUserName());
			log.setTransAmt(transLog.getTransAmt());// 实际交易金额 插入时候默认与上送金额一致
			log.setTransCurrCd(Constants.TRANS_CURR_CD);
			log.setOrgWxPrimaryKey(StringUtil.nullToString(itfLog.getDmsRelatedKey())); // 外面流水号
			int i = wxTransLogService.insertWxTransLog(log);// 插入流水记录
			if (i != 1) {
				logger.info("申请退款--->微信端插入流水记录数量不为1");
				return resultHtml;
			}

		} catch (Exception ex) {
			resultHtml.setStatus(false);
			resultHtml.setMsg("参数校验校验失败");
			logger.error("申请退款--->参数校验校验失败", ex);
		}

		// ========================外部系统退款 begin========================
		String transChnl = "";// 请求渠道
		// 微信快捷支付退款
		if (ChannelCode.CHANNEL2.toString().equals(itfLog.getTransChnl())
				&& TransCode.CW71.getCode().equals(itfLog.getTransId())) {
			try {
				transChnl = ChannelCode.CHANNEL2.toString();
				Account account = accountService
						.getByAccount(RedisDictProperties.getInstance().getdictValueByCode("WX_CUSTOMER_ACCOUNT"));
				JSONObject refundResJson = CustomerWxPayApiClient.getRefundOrder(account, itfLog.getDmsRelatedKey(),
						itfLog.getDmsRelatedKey(), itfLog.getTransAmt(), itfLog.getTransAmt(), request);// 申請退款
				if (refundResJson != null) {
					if (refundResJson.containsKey("return_code")
							&& "SUCCESS".equals(refundResJson.getString("return_code"))) {
						payBackResp.setCode(Constants.TransRespEnum.SUCCESS.getCode());
						dms_related_key = refundResJson.getString("transaction_id");
					} else {
						// 如果失败 重新发起退款
						refundResJson = CustomerWxPayApiClient.getRefundOrder(account, itfLog.getDmsRelatedKey(),
								itfLog.getDmsRelatedKey(), itfLog.getTransAmt(), itfLog.getTransAmt(), request);// 申請退款
						if (refundResJson.containsKey("return_code")
								&& "SUCCESS".equals(refundResJson.getString("return_code"))) {
							payBackResp.setCode(Constants.TransRespEnum.SUCCESS.getCode());
							dms_related_key = refundResJson.getString("transaction_id");
						}
					}
				}
			} catch (Exception ex) {
				resultHtml.setStatus(false);
				resultHtml.setMsg("外部系统退款异常");
				logger.error("申请退款--->微信退款款异常", ex);
			}
		} else {

			transChnl = ChannelCode.CHANNEL1.toString();
			// 嘉福发起的交易
			if (ChannelCode.CHANNEL4.toString().equals(itfLog.getTransChnl())) {
				try {
					// 嘉福支付
					Map<String, String> map = new HashMap<String, String>();
					map.put("orderId", itfLog.getDmsRelatedKey());
					map.put("extOrderId", transLog.getDmsRelatedKey());
					map.put("userId", itfLog.getUserInfUserName());
					String timestamp = "" + System.currentTimeMillis();
					map.put("timestamp", timestamp);
					map.put("sign", MD5Util
							.md5("extOrderId=" + transLog.getDmsRelatedKey() + "&orderId=" + itfLog.getDmsRelatedKey()
									+ "&timestamp=" + timestamp + "&userId=" + itfLog.getUserInfUserName() + "&key="
									+ RedisDictProperties.getInstance().getdictValueByCode("PAYBACK_SIGN_KEY"))
							.toUpperCase());
					// String jfResult =
					// java2TxnBusinessFacade.HKBPayBackToJF(bean);
					String jfResult = HKBTxnUtil.hkbPayBackToJF(map);
					if (StringUtil.isNullOrEmpty(jfResult)) {
						logger.info("申请退款--->订单号：[{}]知了企服退款至嘉福账户交易接口返回值为空", translogRefund.getTxnPrimaryKey());
						resultHtml.setMsg(Constants.TransRespEnum.ERROR.getName());
					}
					payBackResp = JSONArray.parseObject(jfResult, TxnResp.class);
					if (payBackResp != null) {
						dms_related_key = payBackResp.getOrderId();
					} else {
						payBackResp = new TxnResp();
					}
				} catch (Exception ex) {
					resultHtml.setStatus(false);
					resultHtml.setMsg("外部系统退款异常");
					logger.error("申请退款--->嘉福支付退款异常", ex);
				}
			} else {
				// 微信端发起的交易
				dms_related_key = itfLog.getDmsRelatedKey();//
				payBackResp.setCode(Constants.TransRespEnum.SUCCESS.getCode());
			}
		}
		// ========================外部系统退款 end========================

		// 请求middleware 进行内部系统退款
		TxnResp resp = new TxnResp();
		TxnPackageBean txnBean = new TxnPackageBean();
		try {
			// 交易成功(00)以及重复交易(98)均为成功交易，调用middleware
			if (Constants.TransRespEnum.SUCCESS.getCode().equals(payBackResp.getCode())
					|| Constants.JF_TRANS_RESP_SUCCESS.equals(payBackResp.getCode())) {

				try {
					Date currDate = DateUtil.getCurrDate();
					txnBean.setSwtFlowNo(log.getWxPrimaryKey()); // 接口平台交易流水号
					txnBean.setTxnType(log.getTransId() + "0");// 0: 同步 1:异步
					txnBean.setSwtTxnDate(DateUtil.getStringFromDate(currDate, DateUtil.FORMAT_YYYYMMDD));
					txnBean.setSwtTxnTime(DateUtil.getStringFromDate(currDate, DateUtil.FORMAT_HHMMSS));
					txnBean.setSwtSettleDate(log.getSettleDate());
					txnBean.setChannel(transChnl); //
					txnBean.setIssChannel(transLog.getInsCode()); // 机构渠道号
					txnBean.setInnerMerchantNo(transLog.getMchntCode()); // 商户号
					txnBean.setInnerShopNo(transLog.getShopCode());
					txnBean.setCardNo("U" + transLog.getUserName()); // U+UserName
					txnBean.setTxnAmount(transLog.getTransAmt()); // 交易金额
					txnBean.setOriTxnAmount(transLog.getTransAmt());
					txnBean.setTimestamp(System.currentTimeMillis());// 时间戳
					txnBean.setOriSwtFlowNo(transLog.getDmsRelatedKey());
					long currentTime = System.currentTimeMillis();
					txnBean.setTimestamp(currentTime);
					String signature = SignatureUtil.genB2CTransMsgSignature(txnBean.getSwtSettleDate(),
							txnBean.getSwtFlowNo(), txnBean.getIssChannel(), txnBean.getInnerMerchantNo(),
							txnBean.getTxnAmount(), txnBean.getOriSwtFlowNo(), txnBean.getOriTxnAmount(),
							txnBean.getCardNo(), txnBean.getPinTxn(), txnBean.getTimestamp());
					txnBean.setMac(signature);

					// 远程调用退款接口
					String json = java2TxnBusinessFacade.consumeRefundITF(txnBean); // 退款接口
					resp = JSONArray.parseObject(json, TxnResp.class);
					if (resp == null || StringUtil.isNullOrEmpty(resp.getCode())) {
						json = java2TxnBusinessFacade.transExceptionQueryITF(log.getWxPrimaryKey());
						resp = JSONArray.parseObject(json, TxnResp.class);
					}

				} catch (Exception ex) {
					logger.error("申请退款--->系统故障", ex);
				}

				try {
					logger.info("resp  is -------------->" + JSONArray.toJSONString(resp));

					log.setTransSt(1);// 插入时为0，报文返回时更新为1
					log.setRespCode(resp.getCode());
					log.setTransAmt(resp.getTransAmt());// 交易核心返回的优惠后实际交易金额
					log.setDmsRelatedKey(dms_related_key);// 外部流水号
					wxTransLogService.updateWxTransLog(log, resp);

				} catch (Exception ex) {
					logger.error("更新退款请求返回码--->系统故障", ex);
				}

				if (!Constants.TransRespEnum.SUCCESS.getCode().equals(resp.getCode())) {
					// 出现异常情况的处理
					logger.info("申请退款--->消费交易撤销失败", resp.getInfo());
					resultHtml.setStatus(false);
					resultHtml.setMsg(resp.getInfo());
					return resultHtml;
				} else {
					resultHtml.setStatus(true);
				}
			} else if (Constants.JF_TRANS_RESP_SUCCESS.equals(payBackResp.getCode())) {
				resultHtml.setMsg(Constants.JF_PAY_BACK_REPEAT_DESC);
			} else {
				resultHtml.setMsg(payBackResp.getInfo());
			}
		} catch (Exception ex) {
			resultHtml.setStatus(false);
			resultHtml.setMsg(Constants.JF_PAY_BACK_REPEAT_DESC);
			logger.error("申请退款--->系统故障", ex);
		} finally {
			try {
				if (!Constants.TransRespEnum.SUCCESS.getCode().equals(resp.getCode())) { // 多次退款不成功后发短信给开发人员
					String phonesStr = RedisDictProperties.getInstance()
							.getdictValueByCode("SYSTEM_MONITORING_USER_PHONES");
					String[] users = phonesStr.split(",");
					if (users != null) {
						for (int i = 0; i < users.length; i++) {
							messageService.sendMessage(users[i],
									"【知了企服】在商户退款时出现故障,微信流水号:" + log.getWxPrimaryKey() + ",请及时处理!");
						}
					}
				}
			} catch (Exception ex) {
				logger.error("申请退款--->系统故障", ex);
			}
		}

		// 退款成功，发送客服消息
		if (Constants.TransRespEnum.SUCCESS.getCode().equals(resp.getCode())) {
			if (ChannelCode.CHANNEL1.toString().equals(itfLog.getTransChnl())
					|| ChannelCode.CHANNEL2.toString().equals(itfLog.getTransChnl())) {
				try {
					String txnDate = DateUtil.getChineseDateFormStr(txnBean.getSwtTxnDate() + txnBean.getSwtTxnTime());
					
					wechatMQProducerService.sendTemplateMsg(
							RedisDictProperties.getInstance().getdictValueByCode("WX_CUSTOMER_ACCOUNT"),
							log.getUserInfUserName(), "WX_TEMPLATE_ID_4", null,
							WXTemplateUtil.setRefundData(txnBean.getSwtFlowNo(), itfLog.getMchntName(), itfLog.getShopName(), 
									NumberUtils.RMBCentToYuan(log.getTransAmt()), txnDate, templateMsgRefund.findByCode(itfLog.getTransChnl()).getValue(), itfLog.getShopName()));
				} catch (Exception ex) {
					logger.error("申请退款--->客服消息发送失败", ex);
				}
			}
		}
		return resultHtml;
	}

	/**
	 * 检查手机动态口令是否正确
	 * 
	 * @param phoneCode
	 *            手机动态口令
	 * @param type
	 *            业务类型
	 */
	protected boolean checkPhoneCode(String phoneCode, ResultHtml result) {
		if (StringUtil.isNullOrEmpty(phoneCode)) {
			result.setStatus(Boolean.FALSE);
			result.setMsg(MessageUtil.ERROR_MESSAGE_PHONE_CODE + "不能为空");
			return false;
		}
		String sysPhoneCode = JedisClusterUtils.getInstance().get(WxCmsContents.SESSION_PHONECODE);
		if (sysPhoneCode == null) {// session过期
			result.setStatus(Boolean.FALSE);
			result.setMsg("请先发送动" + MessageUtil.ERROR_MESSAGE_PHONE_CODE + "至您的手机");
			return false;
		}
		if (!sysPhoneCode.equalsIgnoreCase(phoneCode)) {
			result.setStatus(Boolean.FALSE);
			result.setMsg(MessageUtil.ERROR_MESSAGE_PHONE_CODE + "不正确，请重新输入");
			return false;
		}
		Long expireTime = Long.valueOf(JedisClusterUtils.getInstance().get(WxCmsContents.SESSION_PHONECODE_TIME));
		if (DateUtil.getCurrDate().getTime() > expireTime) {
			result.setStatus(Boolean.FALSE);
			result.setMsg(MessageUtil.ERROR_MESSAGE_PHONE_CODE + "失效，请重新申请");
			return false;
		}
		return true;
	}

	@Override
	public String transOrderRefund(OrderRefund req) {
		logger.info("退款接口--->请求参数[{}]", JSONArray.toJSONString(req));
		
		OrderRefund resp = new OrderRefund();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setMsg(BaseConstants.RESPONSE_EXCEPTION_INFO);
		
		String validMsg = WxTransOrderTxnReqValid.transOrderRefundValid(req);
		if (!StringUtil.isNullOrEmpty(validMsg)) {
			logger.error("## 退款接口--->{}", validMsg);
			resp.setMsg(validMsg);
			return JSONArray.toJSONString(resp);
		}
		
		WxTransOrder wxOrder = new WxTransOrder();
		wxOrder.setDmsRelatedKey(req.getOriOrderId());
		wxOrder.setTransChnl(req.getChannel());
		wxOrder.setOrgOrderKey("W10");
		wxOrder.setDataStat("0");
		WxTransOrder wxTransOrder = null;
		try {
			wxTransOrder = wxTransOrderMapper.getWxTransOrdeByWxTransOrder(wxOrder);
			if (wxTransOrder == null) {
				logger.error("## 退款接口--->原交易订单号[{}]在WxTransOrder中不存在", req.getOriOrderId());
				resp.setMsg("原交易订单不存在");
				return JSONArray.toJSONString(resp);
			}
		} catch (Exception e) {
			logger.error("## 退款接口--->查询WxTransOrder中原交易订单号[{}]信息异常{}", req.getOriOrderId(), e);
			return JSONArray.toJSONString(resp);
		}
		
		WxTransOrderDetail wxTransOrderDetail = wxTransOrderMapper.getWxTransOrdeDetailByOrderId(wxTransOrder.getOrderKey());
		if (wxTransOrderDetail == null) {
			logger.error("## 退款接口--->原交易订单号[{}]在WxTransOrderDetail中不存在", req.getOriOrderId());
			resp.setMsg("原交易订单不存在");
			return JSONArray.toJSONString(resp);
		}
		
		WxTransLog wxTransLog = new WxTransLog();
		wxTransLog.setOrderId(wxTransOrder.getOrderKey());
		wxTransLog.setTransChnl(req.getChannel());
		WxTransLog wxLog = wxTransLogService.getWxTransLogByWxTransLog(wxTransLog);
		if (wxLog == null) {
			logger.error("## 退款接口--->原交易订单号{}信息在WxTransLog中不存在", JSONArray.toJSONString(wxTransLog));
			resp.setMsg("原交易订单不存在");
			return JSONArray.toJSONString(resp);
		}
		
		InterfaceTrans itfTransLog = new InterfaceTrans();
		itfTransLog.setDmsRelatedKey(wxLog.getWxPrimaryKey());
		InterfaceTrans itfLog = transLogService.getInterfaceTransByInterfaceTrans(itfTransLog);
		if (itfLog == null) {
			logger.error("## 退款接口--->原交易订单号{}信息在InterfaceTransLog中不存在", JSONArray.toJSONString(itfTransLog));
			resp.setMsg("原交易订单不存在");
			return JSONArray.toJSONString(resp);
		}
		
		CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
		if (cs == null || !Constants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {
			logger.error("## 退款接口--->日切信息交易允许状态：日切中");
			return JSONArray.toJSONString(resp);
		}
		
		WxTransOrder transOrder = new WxTransOrder();
		String orderKey = wxTransLogService.getPrimaryKey();
		transOrder.setOrderKey(orderKey);
		transOrder.setOrgOrderKey(wxTransOrder.getOrderKey());
		transOrder.setDmsRelatedKey(req.getRefundOrderId());
		transOrder.setOrgDmsRelatedKey(wxTransOrder.getDmsRelatedKey());
		transOrder.setInsCode(wxTransOrder.getInsCode());
		transOrder.setMchntCode(wxTransOrder.getMchntCode());
		transOrder.setShopCode(wxTransOrder.getShopCode());
		transOrder.setSponsor("00");
		transOrder.setUserId(wxTransOrder.getUserId());
		transOrder.setUserInfUserName(wxTransOrder.getUserInfUserName());
		transOrder.setTransCurrCd(wxTransOrder.getTransCurrCd());
		transOrder.setTransChnl(wxTransOrder.getTransChnl());
		transOrder.setOrderAmt(req.getRefundAmount());
		transOrder.setDataStat("0");
		if (wxTransOrderMapper.insertWxTransOrder(transOrder) < 1) {
			logger.error("## 退款接口--->插入WxTransOrder退款订单记录失败,退款订单号[{}]", req.getRefundOrderId());
			return JSONArray.toJSONString(resp);
		}
		
		WxTransOrderDetail transOrderDetail = new WxTransOrderDetail();
		String orderDetailId = wxTransLogService.getPrimaryKey();
		transOrderDetail.setOrderListId(orderDetailId);
		transOrderDetail.setOrderKey(transOrder.getOrderKey());
		transOrderDetail.setOrgTxnFlowNo(wxTransOrderDetail.getTxnFlowNo());
		// 原交易为会员卡消费
		if (TransCode.CW10.getCode().equals(wxTransOrderDetail.getTransId())) {
			transOrderDetail.setTransId(TransCode.CW30.getCode());// 交易类型
		}
		// 原交易为快捷消费
		if (TransCode.CW71.getCode().equals(wxTransOrderDetail.getTransId())) {
			transOrderDetail.setTransId(TransCode.CW75.getCode());// 交易类型
		}
		transOrderDetail.setTransSt("0");
		transOrderDetail.setCommodityName(wxTransOrderDetail.getCommodityName());
		transOrderDetail.setCommodityNum(wxTransOrderDetail.getCommodityNum());
		transOrderDetail.setTransAmt(req.getRefundAmount());
		transOrderDetail.setUploadAmt(req.getRefundAmount());
		transOrderDetail.setSignType(wxTransOrderDetail.getSignType());
		transOrderDetail.setNotifyType(wxTransOrderDetail.getNotifyType());
		transOrderDetail.setNotifyUrl(wxTransOrderDetail.getNotifyUrl());
		transOrderDetail.setRedirectType(wxTransOrderDetail.getRedirectType());
		transOrderDetail.setRedirectUrl(wxTransOrderDetail.getRedirectUrl());
		if (wxTransOrderMapper.insertWxTransOrderDetail(transOrderDetail) < 1) {
			logger.error("## 退款接口--->插入WxTransOrderDetail退款订单明细记录失败,退款订单号[{}]", req.getRefundOrderId());
			return JSONArray.toJSONString(resp);
		}
		
		
		WxTransLog addWxLog = new WxTransLog();
		String id = wxTransLogService.getPrimaryKey();
		addWxLog.setWxPrimaryKey(id);
		addWxLog.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
		addWxLog.setSettleDate(cs.getSettleDate());// 交易日期
		addWxLog.setOrgWxPrimaryKey(wxLog.getWxPrimaryKey());
		addWxLog.setOrgOrderId(wxLog.getOrderId());
		addWxLog.setOrderId(transOrder.getOrderKey());
		// 原交易为会员卡消费
		if (TransCode.CW10.getCode().equals(wxLog.getTransId())) {
			addWxLog.setTransId(TransCode.CW30.getCode());// 交易类型
		}
		// 原交易为快捷消费
		if (TransCode.CW71.getCode().equals(wxLog.getTransId())) {
			addWxLog.setTransId(TransCode.CW75.getCode());// 交易类型
		}
		addWxLog.setTransSt(0);// 插入时为0，报文返回时更新为1
		addWxLog.setInsCode(wxLog.getInsCode());// 客户端传过来的机构code
		addWxLog.setMchntCode(wxLog.getMchntCode());
		addWxLog.setShopCode(wxLog.getShopCode());
		addWxLog.setSponsor(wxLog.getSponsor());
		addWxLog.setOperatorOpenId(wxLog.getUserInfUserName());
		addWxLog.setTransChnl(wxLog.getTransChnl()); // 交易渠道
		addWxLog.setUserInfUserName(wxLog.getUserInfUserName());
		addWxLog.setTransAmt(req.getRefundAmount());// 实际交易金额,插入时候默认与上送金额一致
		addWxLog.setUploadAmt(req.getRefundAmount());
		addWxLog.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
		if (wxTransLogService.insertWxTransLog(addWxLog) < 1){// 插入流水记录
			logger.error("## 退款接口--->插入WxTransLog退款流水记录失败,退款订单号[{}]", req.getRefundOrderId());
			return JSONArray.toJSONString(resp);
		}
		
		// ========================外部系统退款 end========================
		//请求middleware 进行内部系统退款
		TxnPackageBean txnBean = new TxnPackageBean();
		TxnResp txnResp = new TxnResp();
		try{
			Date currDate = DateUtil.getCurrDate();
			txnBean.setSwtFlowNo(addWxLog.getWxPrimaryKey());
			// 原交易为快捷消费
			if (TransCode.CW10.getCode().equals(wxLog.getTransId())) {
				txnBean.setTxnType(TransCode.CW30.getCode() + "0");
			}
			// 原交易为会员卡消费  交易类型（0: 同步 1:异步）
			if (TransCode.CW71.getCode().equals(wxLog.getTransId())) {
				txnBean.setTxnType(TransCode.CW75.getCode() + "0");
			}
			txnBean.setSwtTxnDate(DateUtil.getStringFromDate(currDate, DateUtil.FORMAT_YYYYMMDD));
			txnBean.setSwtTxnTime(DateUtil.getStringFromDate(currDate, DateUtil.FORMAT_HHMMSS));
			txnBean.setSwtSettleDate(cs.getSettleDate());// 交易日期
			txnBean.setChannel(req.getChannel());   //请求渠道
			txnBean.setInnerMerchantNo(itfLog.getMchntCode()); // 商户号
			txnBean.setInnerShopNo(itfLog.getShopCode());	//门店号
			txnBean.setTxnAmount(req.getRefundAmount()); // 退款金额
			txnBean.setIssChannel(itfLog.getInsCode()); // 机构号
			txnBean.setCardNo("U" + itfLog.getUserInfUserName()); // U+UserName
			txnBean.setOriTxnAmount(itfLog.getTransAmt());	//原交易金额
			txnBean.setOriSwtFlowNo(itfLog.getInterfacePrimaryKey());//原交易流水号（itf）
			txnBean.setTimestamp(System.currentTimeMillis());// 时间戳
			String sign = SignatureUtil.genB2CTransMsgSignature(txnBean.getSwtSettleDate(),
					txnBean.getSwtFlowNo(), txnBean.getIssChannel(), txnBean.getInnerMerchantNo(),
					txnBean.getTxnAmount(), txnBean.getOriSwtFlowNo(), txnBean.getOriTxnAmount(),
					txnBean.getCardNo(), txnBean.getPinTxn(), txnBean.getTimestamp());
			txnBean.setMac(sign);

			//远程调用支持隔日退款接口
			String json = null;
			try {
				json = java2TxnBusinessFacade.transRefundITF(txnBean); //退款接口
			} catch (Exception e) {
				logger.error("## 退款接口--->远程调用知了企服退款接口异常{}, wx退款流水[{}], itf原交易流水[{}]", e, addWxLog.getWxPrimaryKey(), itfLog.getInterfacePrimaryKey());
			}
			logger.info("退款接口--->远程调用退款接口, 返回参数[{}]", json);
			txnResp = JSONArray.parseObject(json, TxnResp.class);
			
			//接收知了企服退款接口返回值，判断是否为空,并调用查询接口判断
			try {
				json = java2TxnBusinessFacade.transExceptionQueryITF(addWxLog.getWxPrimaryKey());
			} catch (Exception e) {
				logger.error("## 退款接口--->远程调用知了企服订单查询接口异常{}, wx退款流水[{}]", e, addWxLog.getWxPrimaryKey());
			}
			logger.info("退款接口--->远程调用知了企服订单查询接口, 返回参数[{}]", json);
			txnResp = JSONArray.parseObject(json, TxnResp.class);
			if (txnResp != null) {
				transOrderDetail.setRespCode(txnResp.getCode());
				transOrderDetail.setTxnFlowNo(txnResp.getInterfacePrimaryKey());
				if (!StringUtil.isNullOrEmpty(txnResp.getTransAmt())) {
					transOrderDetail.setTransAmt(txnResp.getTransAmt());
				}
			}
			transOrderDetail.setTransSt("1");// 插入时为0，报文返回时更新为1
			if (wxTransOrderMapper.updateWxTransOrderDetail(transOrderDetail) < 1) {
				logger.error("## 退款接口--->远程调用知了企服退款接口更新WxTransOrderDetail订单明细号[{}]失败", transOrderDetail.getOrderListId());
			}
			if (wxTransLogService.updateWxTransLog(addWxLog, txnResp) < 1) {
				logger.error("## 退款接口--->远程调用知了企服退款接口更新WxTransLog流水[{}]失败", addWxLog.getWxPrimaryKey());
			}
		} catch(Exception e) {
			logger.error("## 退款接口--->外部退款订单号{}，申请退款异常{}", req.getRefundOrderId(), e);
			return JSONArray.toJSONString(resp);
		} finally {
			try{
				if (!BaseConstants.RESPONSE_SUCCESS_CODE.equals(txnResp.getCode())) { //退款不成功后发短信给开发人员
					String phonesStr = RedisDictProperties.getInstance().getdictValueByCode("SYSTEM_MONITORING_USER_PHONES");
					String[] users = phonesStr.split(",");
					if(users != null){
						for(int i=0; i<users.length; i++){
							messageService.sendMessage(users[i],"【知了企服】在退款时出现故障,wx退款交易流水号:" + addWxLog.getWxPrimaryKey() + ",请及时处理!");
						}
					}
				}
			} catch (Exception e) {
				logger.error("## 退款接口--->申请退款异常{}", e);
			}
		}
		//退款成功，发送模板消息
		if (BaseConstants.RESPONSE_SUCCESS_CODE.equals(txnResp.getCode())) {
			String payAmt = NumberUtils.RMBCentToYuan(req.getRefundAmount());
			ShopInf shopInf = shopInfService.getShopInfByCode(itfLog.getShopCode());
			//根据退款标志显示模板文案Title
			String refundDesc = templateMsgRefund.templateMsgRefund1.getValue();
			if (refundFalg.refundFalg1.getCode().equals(req.getRefundFlag())) {
				refundDesc = templateMsgRefund.findByCode(req.getChannel()).getValue();
			}
			//显示退款时间
			String txnDate = null;
			try {
				txnDate = DateUtil.getChineseDateFormStr(txnBean.getSwtTxnDate() + txnBean.getSwtTxnTime());
			} catch (Exception e) {
				logger.error("## 退款接口--->发送模板消息出错, 时间计算异常{} ", e);
			}
			//模板消息显示渠道名称
			String channelName = shopInf.getShopName();
			if (ChannelCode.CHANNEL6.toString().equals(req.getChannel())) {
				if (!StringUtil.isNullOrEmpty(req.getChannelName())) {
					channelName = req.getChannelName();
				}
			} else if (ChannelCode.CHANNEL8.toString().equals(req.getChannel())) {
				channelName = templateMsgPayment.findByCode(req.getChannel()).getValue();
			} else if (ChannelCode.CHANNEL9.toString().equals(req.getChannel())) {
				channelName = templateMsgPayment.findByCode(req.getChannel()).getValue();
			}
			try {
				wechatMQProducerService.sendTemplateMsg(RedisDictProperties.getInstance().getdictValueByCode("WX_CUSTOMER_ACCOUNT"), itfLog.getUserInfUserName(), "WX_TEMPLATE_ID_4", null, 
						WXTemplateUtil.setRefundData(txnResp.getInterfacePrimaryKey(), shopInf.getMchntName(), shopInf.getShopName(), payAmt, txnDate, refundDesc, channelName));
			} catch (Exception e) {
				logger.error("## 退款接口--->发送模板消息异常{} ", e);
			}
		}
		resp.setCode(txnResp.getCode());
		resp.setMsg(txnResp.getInfo());
		resp.setTxnFlowNo(txnResp.getInterfacePrimaryKey());
		return JSONArray.toJSONString(resp);
	}

	@Override
	public WxTransOrder getWxTransOrdeByWxTransOrder(WxTransOrder transOrder) {
		return wxTransOrderMapper.getWxTransOrdeByWxTransOrder(transOrder);
	}
	
}
