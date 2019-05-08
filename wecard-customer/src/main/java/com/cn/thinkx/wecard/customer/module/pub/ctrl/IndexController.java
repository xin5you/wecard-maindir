package com.cn.thinkx.wecard.customer.module.pub.ctrl;

import com.cn.thinkx.common.activemq.service.WechatMQProducerService;
import com.cn.thinkx.common.wecard.domain.channeluser.ChannelUserInf;
import com.cn.thinkx.facade.service.HKBTxnFacade;
import com.cn.thinkx.pms.base.redis.util.BaseKeyUtil;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.DES3Util;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.service.txn.Java2TxnBusinessFacade;
import com.cn.thinkx.wecard.customer.module.checkstand.service.WxTransOrderService;
import com.cn.thinkx.wecard.customer.module.customer.service.*;
import com.cn.thinkx.wecard.customer.module.merchant.service.MerchantInfService;
import com.cn.thinkx.wecard.customer.module.merchant.service.MerchantManagerService;
import com.cn.thinkx.wecard.customer.module.merchant.service.ShopInfService;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping()
public class IndexController {
    Logger logger = LoggerFactory.getLogger(IndexController.class);


    @Autowired
    @Qualifier("hkbTxnFacade")
    private HKBTxnFacade hkbTxnFacade;


    @Autowired
    @Qualifier("wxTransLogService")
    private WxTransLogService wxTransLogService;


    @Autowired
    @Qualifier("ctrlSystemService")
    private CtrlSystemService ctrlSystemService;

    @Autowired
    @Qualifier("wxTransOrderService")
    private WxTransOrderService wxTransOrderService;

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
    @Qualifier("customerManagerService")
    private CustomerManagerService customerManagerService;

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("info", "欢迎来到薪无忧公众号后台管理系统，敬请期待...");
//		req.setChannel(CustomerConstants.ChannelCode.CHANNEL4.toString());
//		
//		req.setInnerMerchantNo("201610310000001");
//		req.setUserId("53627");
//		req.setTimestamp(System.currentTimeMillis());
//		req.setSign(SignUtil.genSign(req));
//		
//		try {
//			logger.info("--------------------------------------------");
//			json = hkbTxnFacade.cardBalanceQueryITF(req);
//			System.out.println(json);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

//		RechargeTransRequest req = new RechargeTransRequest();
//		req.setChannel(ChannelCode.CHANNEL4.toString());
//		req.setSwtTxnDate("20170517");
//		req.setSwtTxnTime(DateUtil.getCurrentTimeStr());
//		req.setSwtFlowNo("E"+System.currentTimeMillis());
//		req.setInnerMerchantNo("101000000252634");
//		req.setCommodityCode("2017051709504500000207");
//		req.setCommodityNum("1");
//		req.setCardNo("53627");
//		req.setTxnAmount("100");
//		req.setOriTxnAmount("1");
//		req.setTimestamp(System.currentTimeMillis());
//		req.setSign(SignUtil.genSign(req));
//		
//		try {
//			logger.info("--------------------------------------------");
//			json = hkbTxnFacade.rechargeTransactionITF(req);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

        return mv;
    }

    @RequestMapping(value = "/om")
    public ModelAndView index(@RequestParam(value = "p") String p) {
        String[] paramArr = null;
        try {
            if (StringUtil.isNullOrEmpty(p))
                return null;// TODO 跳转至无法使用二维码页面
            paramArr = DES3Util.Decrypt3DES(p, BaseKeyUtil.getEncodingAesKey()).split("\\|");
        } catch (Exception e) {
            logger.error("商户收银台-->收款码二维码解密失败", e);
        }
        String mchntCode = paramArr[0];
        String shopCode = paramArr[1];
        if (paramArr.length > 2) {
            String channl = paramArr[2];
            if ("JF".equals(channl)) {
                String JFUserId = paramArr[3];
                String HKBUserId = paramArr[4];
                String parm = "?mchntCode=" + StringUtil.nullToString(mchntCode) + "&shopCode=" + StringUtil.nullToString(shopCode) + "&JFUserId=" + StringUtil.nullToString(JFUserId) + "&HKBUserId=" + StringUtil.nullToString(HKBUserId);
                return new ModelAndView("redirect:/jfPay/scanCode.html" + parm);
            }
        }
        return new ModelAndView("redirect:/customer/user/scanCode.html?mchntCode=" + StringUtil.nullToString(mchntCode) + "&shopCode=" + StringUtil.nullToString(shopCode));
    }

//	@RequestMapping(value = "/hkbConsumeTransactionITF")
//	@ResponseBody
//	public TxnResp hkbConsumeTransactionITF(HttpServletRequest request) {
//		TxnResp resp = new TxnResp();
//		
//		String sponsor = request.getParameter("sponsor");
//		String openid = request.getParameter("openid");
//		String merchantCode = request.getParameter("merchantCode");
//		String shopCode = request.getParameter("shopCode");
//		String insCode = request.getParameter("insCode");
//		String transMoney = request.getParameter("transMoney");
//		String payType= request.getParameter("payType");
//		
//		CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
//		WxTransLog log = new WxTransLog();
//		if (cs != null) {
//			if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入微信端流水
//				String id = wxTransLogService.getPrimaryKey();
//				log.setTableNum(cs.getCurLogNum());
//				log.setWxPrimaryKey(id);
//				log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
//				log.setSettleDate(cs.getSettleDate());// 交易日期
//				if(StringUtil.isNullOrEmpty(payType) || "VIPCARD_PAY".equals(payType)){
//					log.setTransId(TransCode.CW10.getCode());// 交易类型 会员卡支付
//					log.setTransChnl(ChannelCode.CHANNEL1.toString());
//				}else{
//					log.setTransId(TransCode.CW71.getCode());// 交易类型 微信快捷支付
//					log.setTransChnl(ChannelCode.CHANNEL2.toString());
//				}
//				log.setTransSt(0);// 插入时为0，报文返回时更新为1
//				log.setInsCode(insCode);// 客户端传过来的机构code 
//				log.setMchntCode(merchantCode);
//				log.setShopCode(shopCode);
//				log.setSponsor(sponsor);
//				log.setOperatorOpenId(openid);
//				log.setUserInfUserName(openid);
//				transMoney =NumberUtils.RMBYuanToCent(transMoney);// 原交易金额单位元转分
//				log.setTransAmt(transMoney);// 实际交易金额 插入时候默认与上送金额一致
//				log.setUploadAmt(transMoney);// 上送金额
//				log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
//				int i = wxTransLogService.insertWxTransLog(log);// 插入流水记录
//				if (i != 1) {
//					logger.info("消费交易--->insertIntfaceTransLog微信端插入流水记录数量不为1");
//					return null;
//				}
//			} else {
//				logger.info("消费交易--->日切信息交易允许状态：日切中");
//				return null;
//			}
//		}
//		
//		/**是否需要输入密码*/
//		UserMerchantAcct userMerchantAcct = new UserMerchantAcct();
//		userMerchantAcct.setExternalId(openid);
//		userMerchantAcct.setMchntCode(merchantCode);
//		
//		TxnPackageBean txnBean = new TxnPackageBean();
//		WxTransLog wxTransLog=wxTransLogService.getWxTransLogById(log.getWxPrimaryKey());
//		int transAmt=0;
//		try {
//			transAmt = wxDroolsExcutor.getConsumeDiscount(userMerchantAcct.getMchntId(), null, Integer.parseInt(log.getTransAmt()));// 调用规则引擎
//			
//			//无PIN限额 需要验密
//			boolean noPinTxnSt=userMerchantAcctService.doCustomerNeed2EnterPassword(userMerchantAcct, transAmt);
//			if (!noPinTxnSt) {// 如果实际消费金额大于无PIN限额 需要验密
//				UserInf user=userInfService.getUserInfByOpenId(openid);
//				
//				wxTransLog.setTableNum(cs.getCurLogNum());
//				txnBean.setTxnType(wxTransLog.getTransId() + "0");// 交易类型，发送报文时补0
//				txnBean.setSwtTxnDate(DateUtil.getCurrentDateStr());// 交易日期
//				txnBean.setSwtTxnTime(DateUtil.getCurrentTimeStr());// 交易时间
//				txnBean.setSwtSettleDate(wxTransLog.getSettleDate());// 清算日期
//				txnBean.setSwtFlowNo(wxTransLog.getWxPrimaryKey());
//				txnBean.setIssChannel(wxTransLog.getInsCode());// 机构渠道号
//				txnBean.setInnerMerchantNo(wxTransLog.getMchntCode());// 商户号
//				txnBean.setInnerShopNo(wxTransLog.getShopCode());// 门店号
//				txnBean.setTxnAmount(wxTransLog.getTransAmt());// 交易金额
//				txnBean.setOriTxnAmount(wxTransLog.getUploadAmt());// 原交易金额
//				txnBean.setCardNo("U" + openid);// 卡号 U开头为客户端交易，C开头则为刷卡交易
//				txnBean.setUserId(user.getUserId());// 用户id
//				
//				txnBean.setChannel(ChannelCode.CHANNEL1.toString());// 渠道号
//				txnBean.setTimestamp(System.currentTimeMillis());// 时间戳
//				String signature =TxnChannelSignatureUtil.genSign(txnBean); //生成的签名
//				txnBean.setSignature(signature);
//			}
//		} catch(Exception e) {
//			logger.error("获取无pin限额视图出错", e);
//			
//		}
//		
//		// 远程调用消费接口
//		String json = new String();
//		
//		try {
//			json = java2TxnBusinessFacade.consumeTransactionITF(txnBean);
//			resp = JSONArray.parseObject(json, TxnResp.class);
//			if(resp ==null || !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())){
//				json = java2TxnBusinessFacade.transExceptionQueryITF(wxTransLog.getWxPrimaryKey());
//				resp = JSONArray.parseObject(json, TxnResp.class);
//			}
//		} catch(Exception e) {
//			logger.error("远程调用消费接口异常" + json + "流水号：" + wxTransLog.getWxPrimaryKey(), e);
//		}
//		
//		// 更新微信端流水
//		try{
//			if(resp==null){
//				resp=new TxnResp();
//				resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
//				resp.setInfo("对不起，交易出现异常，请联系客服人员人工处理");
//			}
//			wxTransLogService.updateWxTransLog(wxTransLog,resp);
//		} catch(Exception ex) {
//			logger.error("消费交易更新微信流水异常", ex);
//		}
//		
//		return resp;
//	}

    @RequestMapping(value = "/olderUsersOpenHKBCard")
    @ResponseBody
    public TxnResp userRegAndOpenCard(HttpServletRequest request) {
        TxnResp resp = new TxnResp();
        resp.setCode(BaseConstants.TXN_TRANS_RESP_SUCCESS);
        List<ChannelUserInf> olderUsersList = userInfService.getAllChannelUsers();
        int i = 0;
        for (ChannelUserInf obj : olderUsersList) {
            if ("ACC_HKB".equals(obj.getRemarks()))
                continue;

            try {
                TxnResp HKB_RESP = customerManagerService.doHKBAccountOpening(obj.getUserId(), null);
                if (HKB_RESP == null) {
                    logger.info("老用户userid[{}]批量开通卡失败", obj.getUserId());
                } else if (BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(HKB_RESP.getCode())) {
                    obj.setRemarks("ACC_JF");
                    i += userInfService.updateChannelUserInf(obj);
                } else {
                    logger.info("老用户userid[{}]批量开通卡失败，原因[{}]", obj.getUserId(), HKB_RESP.getInfo());
                }

            } catch (Exception e) {
                logger.error("## 批量开卡异常", e);
            }
        }
        resp.setInfo("批量开卡成功，总开卡数量：" + i);
        return resp;
    }


////	@RequestMapping(value = "/mq")
////	@ResponseBody
////	public String mq() {
////		
////	final String mchntName="MQ商户";
////	final String shopName="MQ门店";
////		
////		
////		for(int i=0;i<20;i++){
////			new Thread(new Runnable(){  
////            @Override  
////            public void run(){
////            	for(int j=0;j<2;j++){
////	            	String txnDate = DateUtil.getCurrentDateStr();
////	            	int tmpAmt=new Random().nextInt(100) +1;
////	        		String payAmt =NumberUtils.RMBCentToYuan(tmpAmt);// 实际支付金额
////	        		String notice_c=String.format(WechatCustomerMessageUtil.WECHAT_CUSTOMER_CW10_SUCCESS,txnDate,mchntName,shopName,payAmt);
////	        		wechatMQProducerService.sendWechatMessage("huikabao_customer",notice_c, "oppGJ0zw1gliL1mr0R1G6FW8JLOA");
////            	}
////	        }}).start();
////		}
////		
////		
////		return "";
////	}
//	
//	
//	@RequestMapping(value = "/rechargeTransactionITF")
//	@ResponseBody
//	public TxnResp rechargeTransactionITF(HttpServletRequest request) {
//		TxnResp returnMap = new TxnResp();
//		String mchntCode = request.getParameter("mchntCode");
//		String openId = request.getParameter("openId");
//		String commodityCode=request.getParameter("commodityCode");
//		
//		MerchantInf merchantInf=merchantInfService.getMerchantInfByCode(mchntCode);
//		
//		WxTransLog log = new WxTransLog();
//		try {
//			/***查询商品信息**/
//			BaseTxnReq baseTxnReq=new BaseTxnReq();
//			baseTxnReq.setInnerMerchantNo(mchntCode);
//			baseTxnReq.setTimestamp(System.currentTimeMillis());
//			baseTxnReq.setSign(SignUtil.genSign(baseTxnReq));
//			
//			String jsonStr=hkbTxnFacade.mchtSellingCardListQueryITF(baseTxnReq);
//			MchtSellingCardListQueryITFResp cardList=JSONArray.parseObject(jsonStr, MchtSellingCardListQueryITFResp.class);
//
//			MchtSellingCardListQueryITFVo cardCommodity=new MchtSellingCardListQueryITFVo();//卡商品信息
//			if(cardList !=null &&  BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(cardList.getCode())){
//				if(cardList.getCardList() !=null && cardList.getCardList().size()>0){
//					for(int i=0;i<cardList.getCardList().size();i++){
//						if(commodityCode.equals(cardList.getCardList().get(i).getCommodityCode())){
//							cardCommodity=cardList.getCardList().get(i);
//							break;
//						}
//					}
//				}
//			}
//			
//			/***客户账户查询***/
//			CusAccQueryRequest cusAccReq=new CusAccQueryRequest();
//			cusAccReq.setUserId(openId);
//			cusAccReq.setInnerMerchantNo(mchntCode);
//			cusAccReq.setChannel(ChannelCode.CHANNEL1.toString());
//			cusAccReq.setTimestamp(System.currentTimeMillis());
//			cusAccReq.setSign(SignUtil.genSign(cusAccReq));
//			
//			String customerAccountStr=hkbTxnFacade.customerAccountQueryITF(cusAccReq); //
//			CustomerAccountQueryITFResp customerAccount=JSONArray.parseObject(customerAccountStr, CustomerAccountQueryITFResp.class);
//			if(customerAccount !=null &&  BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(customerAccount.getCode())){
//				String insCode=merchantInfService.getInsCodeByInsId(merchantInf.getInsId());//机构号
//				
//				if("0".equals(customerAccount.getAccountFlag())){
//					returnMap.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
//					return returnMap;  //没开户 直接返回
//				}
//				if("0".equals(customerAccount.getCardFlag())){  //开卡
//					
//					TxnResp resp =userMerchantAcctService.doCustomerAccountOpeningITF(cardCommodity.getProductCode(), null, openId, mchntCode, insCode);
//					if(resp !=null && BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())){
//						returnMap.setCode(BaseConstants.TXN_TRANS_RESP_SUCCESS);
//					}else{
//						returnMap.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
//					}
//				}
//				if("1".equals(customerAccount.getAccountFlag()) && "1".equals(customerAccount.getCardFlag())){
//					CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
//					
//					if (cs != null) {
//						if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入微信端流水
//							String wxPrimaryKey = wxTransLogService.getPrimaryKey();
//							log.setWxPrimaryKey(wxPrimaryKey);
//							log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
//							log.setSettleDate(cs.getSettleDate());// 交易日期
//							log.setTransId(TransCode.CW20.getCode());//客户充值
//							log.setTransSt(0);// 插入时为0，报文返回时更新为1
//							log.setMchntCode(merchantInf.getMchntCode());
//							log.setInsCode(insCode);
//							log.setUserInfUserName(openId);
//							log.setOperatorOpenId(openId);
//							log.setProductCode(cardCommodity.getProductCode()); //商品CODE
//							log.setTransAmt(cardCommodity.getCommodityAmount());// 卡面面额
//							log.setUploadAmt(cardCommodity.getCommodityPrice());// 上送金额
//							log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
//							log.setTransChnl(ChannelCode.CHANNEL2.toString());
//							log.setRemarks(cardCommodity.getCommodityCode()); //商品号
//							int i=wxTransLogService.insertWxTransLog(log);// 插入业务流水(微信端)
//							if (i != 1) {
//								logger.info("购卡充值 微信交易--->insertIntfaceTransLog微信端插入流水记录数量不为1");
//								returnMap.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
//							}
//							
//							RechargeTransRequest buyCardRechargReq=new RechargeTransRequest();
//							buyCardRechargReq.setSwtTxnDate(DateUtil.getCurrentDateStr());// 交易日期
//							buyCardRechargReq.setSwtTxnTime(DateUtil.getCurrentTimeStr());// 交易时间
//							buyCardRechargReq.setSwtSettleDate(log.getSettleDate());// 清算日期
//							buyCardRechargReq.setChannel(ChannelCode.CHANNEL2.toString());
//							buyCardRechargReq.setSwtFlowNo(log.getWxPrimaryKey());
//							buyCardRechargReq.setInnerMerchantNo(log.getMchntCode());
//							buyCardRechargReq.setCommodityCode(log.getRemarks());
//							buyCardRechargReq.setCommodityNum("1");
//							buyCardRechargReq.setCardNo(log.getUserInfUserName());
//							buyCardRechargReq.setTxnAmount(log.getTransAmt()); //交易金额，卡面总金额
//							buyCardRechargReq.setOriTxnAmount(log.getUploadAmt());  //原交易金额，售价总金额
//							try {
//								buyCardRechargReq.setTimestamp(System.currentTimeMillis());
//								buyCardRechargReq.setSign(SignUtil.genSign(buyCardRechargReq));
//								jsonStr = hkbTxnFacade.rechargeTransactionITF(buyCardRechargReq);//远程调用快捷消费接口
//								returnMap = JSONArray.parseObject(jsonStr, TxnResp.class);
//								
//								/***远程调用接口异常处理***/
//								if(returnMap==null){
//									returnMap=new TxnResp();
//								}
//								if(!BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(returnMap.getCode())) {
//									jsonStr=java2TxnBusinessFacade.transExceptionQueryITF(log.getWxPrimaryKey());
//									returnMap = JSONArray.parseObject(jsonStr, TxnResp.class);
//								}
//								
//								try{
//									logger.info("resp  is -------------->" + JSONArray.toJSONString(returnMap));
//									log.setTransSt(1);// 插入时为0，报文返回时更新为1
//									log.setRespCode(returnMap.getCode());
//									wxTransLogService.updateWxTransLog(log.getTableNum(), log.getWxPrimaryKey(),returnMap.getCode(),"");
//								
//								}catch(Exception ex){
//									logger.error("更改交易返回码失败："+ex);
//								}
//								
//							} catch(Exception e) {
//								logger.error("购卡充值 微信支付交易返回通知--->远程调用 微信支付接口异常，流水号：" + log.getWxPrimaryKey(), e);
//							}
//							
//						} else {
//							logger.info("购卡充值 微信交易--->日切信息交易允许状态：日切中");
//							returnMap.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
//						}
//					}
//				}
//			}else{
//				returnMap.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
//				return returnMap;
//			}
//		} catch (Exception e) {
//			logger.error("卡包  购卡充值 提交前检查是否开户开卡-->", e);
//			returnMap.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
//		}
//		
//
//		return returnMap;
//	}
//	
//	@RequestMapping(value = "/quickConsumeTransactionITF")
//	@ResponseBody
//	public TxnResp quickConsumeTransactionITF(HttpServletRequest request) {
//		TxnResp resp = new TxnResp();
//		
//		String sponsor = request.getParameter("sponsor");
//		String openid = request.getParameter("openid");
//		String merchantCode = request.getParameter("merchantCode");
//		String shopCode = request.getParameter("shopCode");
//		String insCode = request.getParameter("insCode");
//		String transMoney = request.getParameter("transMoney");
//		String payType= request.getParameter("payType");
//		
//		CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
//		WxTransLog log = new WxTransLog();
//		if (cs != null) {
//			if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入微信端流水
//				String id = wxTransLogService.getPrimaryKey();
//				log.setTableNum(cs.getCurLogNum());
//				log.setWxPrimaryKey(id);
//				log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
//				log.setSettleDate(cs.getSettleDate());// 交易日期
//				if(StringUtil.isNullOrEmpty(payType) || "VIPCARD_PAY".equals(payType)){
//					log.setTransId(TransCode.CW10.getCode());// 交易类型 会员卡支付
//					log.setTransChnl(ChannelCode.CHANNEL1.toString());
//				}else{
//					log.setTransId(TransCode.CW71.getCode());// 交易类型 微信快捷支付
//					log.setTransChnl(ChannelCode.CHANNEL2.toString());
//				}
//				log.setTransSt(0);// 插入时为0，报文返回时更新为1
//				log.setInsCode(insCode);// 客户端传过来的机构code 
//				log.setMchntCode(merchantCode);
//				log.setShopCode(shopCode);
//				log.setSponsor(sponsor);
//				log.setOperatorOpenId(openid);
//				log.setUserInfUserName(openid);
//				transMoney =NumberUtils.RMBYuanToCent(transMoney);// 原交易金额单位元转分
//				log.setTransAmt(transMoney);// 实际交易金额 插入时候默认与上送金额一致
//				log.setUploadAmt(transMoney);// 上送金额
//				log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
//				int i = wxTransLogService.insertWxTransLog(log);// 插入流水记录
//				if (i != 1) {
//					logger.info("消费交易--->insertIntfaceTransLog微信端插入流水记录数量不为1");
//					return null;
//				}
//			} else {
//				logger.info("消费交易--->日切信息交易允许状态：日切中");
//				return null;
//			}
//		}
//		
//		/**是否需要输入密码*/
//		UserMerchantAcct userMerchantAcct = new UserMerchantAcct();
//		userMerchantAcct.setExternalId(openid);
//		userMerchantAcct.setMchntCode(merchantCode);
//		
//		TxnPackageBean txnBean = new TxnPackageBean();
//		WxTransLog wxTransLog=wxTransLogService.getWxTransLogById(log.getWxPrimaryKey());
//		int transAmt=0;
//		try {
//			transAmt = wxDroolsExcutor.getConsumeDiscount(userMerchantAcct.getMchntId(), null, Integer.parseInt(log.getTransAmt()));// 调用规则引擎
//				UserInf user=userInfService.getUserInfByOpenId(openid);
//				
//				wxTransLog.setTableNum(cs.getCurLogNum());
//				txnBean.setTxnType(wxTransLog.getTransId() + "0");// 交易类型，发送报文时补0
//				txnBean.setSwtTxnDate(DateUtil.getCurrentDateStr());// 交易日期
//				txnBean.setSwtTxnTime(DateUtil.getCurrentTimeStr());// 交易时间
//				txnBean.setSwtSettleDate(wxTransLog.getSettleDate());// 清算日期
//				txnBean.setSwtFlowNo(wxTransLog.getWxPrimaryKey());
//				txnBean.setIssChannel(wxTransLog.getInsCode());// 机构渠道号
//				txnBean.setInnerMerchantNo(wxTransLog.getMchntCode());// 商户号
//				txnBean.setInnerShopNo(wxTransLog.getShopCode());// 门店号
//				txnBean.setTxnAmount(wxTransLog.getTransAmt());// 交易金额
//				txnBean.setOriTxnAmount(wxTransLog.getUploadAmt());// 原交易金额
//				txnBean.setCardNo("U" + openid);// 卡号 U开头为客户端交易，C开头则为刷卡交易
//				txnBean.setUserId(user.getUserId());// 用户id
//				
//				txnBean.setChannel(ChannelCode.CHANNEL2.toString());// 渠道号
//				txnBean.setTimestamp(System.currentTimeMillis());// 时间戳
//				String signature =TxnChannelSignatureUtil.genSign(txnBean); //生成的签名
//				txnBean.setSignature(signature);
//		} catch(Exception e) {
//			e.printStackTrace();
//			
//		}
//		
//		// 远程调用消费接口
//		String json = new String();
//		
//		try {
//			json = java2TxnBusinessFacade.quickConsumeTransactionITF(txnBean);
//			resp = JSONArray.parseObject(json, TxnResp.class);
//			if(resp ==null || !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())){
//				json = java2TxnBusinessFacade.transExceptionQueryITF(wxTransLog.getWxPrimaryKey());
//				resp = JSONArray.parseObject(json, TxnResp.class);
//			}
//		} catch(Exception e) {
//			logger.error("远程调用消费接口异常" + json + "流水号：" + wxTransLog.getWxPrimaryKey(), e);
//		}
//		
//		// 更新微信端流水
//		try{
//			if(resp==null){
//				resp=new TxnResp();
//				resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
//				resp.setInfo("对不起，交易出现异常，请联系客服人员人工处理");
//			}
//			wxTransLogService.updateWxTransLog(wxTransLog,resp);
//		} catch(Exception ex) {
//			logger.error("消费交易更新微信流水异常", ex);
//		}
//		
//		return resp;
//	}
//	
}
