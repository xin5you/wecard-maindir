package com.cn.thinkx.wecard.customer.module.customer.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.beans.TxnPackageBean;
import com.cn.thinkx.common.redis.util.BaseKeyUtil;
import com.cn.thinkx.common.redis.util.RedisConstants;
import com.cn.thinkx.common.redis.util.SignatureUtil;
import com.cn.thinkx.common.wecard.domain.trans.WxTransLog;
import com.cn.thinkx.common.wecard.module.msg.mapper.MsgNewsDao;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransCode;
import com.cn.thinkx.pms.base.utils.DES3Util;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.service.txn.Java2TxnBusinessFacade;
import com.cn.thinkx.wecard.customer.module.base.domain.JsonView;
import com.cn.thinkx.wecard.customer.module.customer.service.CtrlSystemService;
import com.cn.thinkx.wecard.customer.module.customer.service.CustomerManagerService;
import com.cn.thinkx.wecard.customer.module.customer.service.PersonInfService;
import com.cn.thinkx.wecard.customer.module.customer.service.UserMerchantAcctService;
import com.cn.thinkx.wecard.customer.module.customer.service.WxTransLogService;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;
import com.cn.thinkx.wechat.base.wxapi.domain.MsgNews;
import com.cn.thinkx.wechat.base.wxapi.process.WxApiClient;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;

import net.sf.json.JSONObject;
import redis.clients.jedis.JedisCluster;

@Service("customerManagerService")
public class CustomerManagerServiceImpl implements CustomerManagerService {
	private Logger logger = LoggerFactory.getLogger(getClass());
			
	@Autowired
	@Qualifier("userMerchantAcctService")
	private UserMerchantAcctService userMerchantAcctService;
	
	@Autowired
	@Qualifier("personInfService")
	private PersonInfService personInfService;
	
	@Autowired
	@Qualifier("ctrlSystemService")
	private CtrlSystemService ctrlSystemService;
	
	@Autowired
	@Qualifier("wxTransLogService")
	private WxTransLogService wxTransLogService;
	
	@Autowired
	private Java2TxnBusinessFacade java2TxnBusinessFacade;
	
	@Autowired
	@Qualifier("msgNewsDao")
	private MsgNewsDao msgNewsDao;
	
	@Autowired
	@Qualifier("jedisCluster")
	private JedisCluster jedisCluster;

	@Override
	public boolean userRegisterCommit(HttpServletRequest request, String openid, JsonView resp, ModelAndView mvSuccess, 
			String userId, String password, ModelAndView mvFail) throws Exception {
		String phoneNumber = StringUtil.nullToString(request.getParameter("phoneNumber"));// 手机号
		if (StringUtils.isNotEmpty(userId)) { // 密码设置
			CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
			WxTransLog log = new WxTransLog();
			if (cs != null) {
				if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入微信端流水
					try {
						String id = wxTransLogService.getPrimaryKey();
						log.setWxPrimaryKey(id);
						log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
						log.setSettleDate(cs.getSettleDate());// 交易日期
						log.setTransId(TransCode.CW81.getCode());// 密码重置 0: 同步  1:异步
						log.setTransSt(0);// 插入时为0，报文返回时更新为1
						log.setOperatorOpenId(openid);
						int i = wxTransLogService.insertWxTransLog(log);// 插入流水记录
						if (i != 1) {
							resp.setCode("992");
							logger.error("## 用户[{}]注册，密码重置微信端插入流水记录数量不为1", phoneNumber);
							mvFail.addObject("resp", resp);
							return false;
						}
						TxnPackageBean txnBean = new TxnPackageBean();
						Date currDate = DateUtil.getCurrDate();
						txnBean.setSwtFlowNo(log.getWxPrimaryKey()); // 接口平台交易流水号
						txnBean.setTxnType(log.getTransId() + "0");// 密码重置 0: 同步  1:异步
						txnBean.setSwtTxnDate(DateUtil.getStringFromDate(currDate, DateUtil.FORMAT_YYYYMMDD));
						txnBean.setSwtTxnTime(DateUtil.getStringFromDate(currDate, DateUtil.FORMAT_HHMMSS));
						txnBean.setSwtSettleDate(log.getSettleDate());
						txnBean.setChannel(ChannelCode.CHANNEL1.toString()); // 商户开户、客户开户、密码重置、消费(从微信公众号发起)
						txnBean.setInnerMerchantNo(userId);//
						txnBean.setCardNo("U" + openid); // U+UserName
						txnBean.setAccType(BaseConstants.PWDAcctType.CUSTOMER_PWD_100.getCode()); // 账户类型
						txnBean.setPinTxn(DES3Util.Encrypt3DES(password, BaseKeyUtil.getEncodingAesKey()));
						txnBean.setTimestamp(System.currentTimeMillis());// 时间戳
						/*** 生成签名 ***/
						String signature = SignatureUtil.getTxnBeanSignature(txnBean.getSwtSettleDate(),
								txnBean.getSwtFlowNo(), txnBean.getIssChannel(), txnBean.getCardNo(),
								txnBean.getTxnAmount(), txnBean.getPinTxn(), txnBean.getTimestamp());
						txnBean.setSignature(signature);

						/*** 远程调用重置密码接口 ***/
						String json = java2TxnBusinessFacade.customerPasswordResetITF(txnBean);
						resp = JSON.parseObject(json, JsonView.class);
						logger.info("用户[{}]注册，调用ITF会员卡密码重置接口返回{}", phoneNumber, JSON.toJSONString(resp));

						if (resp == null || !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
							json = java2TxnBusinessFacade.transExceptionQueryITF(log.getWxPrimaryKey());
							resp = JSON.parseObject(json, JsonView.class);
							logger.info("用户[{}]注册，交易异常接口返回{}", phoneNumber, JSON.toJSONString(resp));
						}
					} catch (Exception ex) {
						logger.error("## 用户[{}]注册，重置密码失败",phoneNumber , ex);
						wxTransLogService.updateWxTransLog(log.getTableNum(), log.getWxPrimaryKey(), "14", "");
						mvFail.addObject("resp", resp);
						return false;
					}

					try {
						log.setTransSt(1);// 插入时为0，报文返回时更新为1
						log.setRespCode(resp.getCode());
						wxTransLogService.updateWxTransLog(log.getTableNum(), log.getWxPrimaryKey(), resp.getCode(), "");
					} catch (Exception ex) {
						logger.error("## 用户[{}]注册，更改交易返回码失败", phoneNumber, ex);
					}
					if (resp == null || !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
						logger.info(resp.getInfo());
						mvFail.addObject("resp", resp);
						return false;
					} else {
						/** 开卡知了企服通卡 add by pucker 2018/1/30 **/
						try {
							TxnResp HKB_RESP = this.doHKBAccountOpening(userId, null);
							if (HKB_RESP == null)
								return false;
							
							if (!BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(HKB_RESP.getCode())) {
								logger.info(HKB_RESP.getInfo());
								mvFail.addObject("resp", HKB_RESP);
								return false;
							}
						} catch (Exception e) {
							logger.error("## 用户[{}]注册开通知了企服通卡失败", phoneNumber, e);
						}
						
						// 通知用户注册成功
						try {
//							List<MsgNews> newsList = msgNewsDao.getMsgNewsByUserReg(); // 首次关注欢迎语 图文消息
							JSONObject jsonStr = WxApiClient.sendCustomTextMessage(openid, "注册成功",
									WxMemoryCacheClient.getSingleMpAccount());
							logger.info("用户[{}]注册成功发送图文消息{}", phoneNumber, jsonStr.toString());
						} catch (Exception ex) {
							logger.error("## 用户[{}]注册发送客服消息失败", phoneNumber, ex);
						}
					}
				} else {
					resp.setCode("991");
					logger.error("## 用户[{}]注册，日切信息交易允许状态：日切中", phoneNumber);
					mvFail.addObject("resp", resp);
					return false;
				}
			}
		}
		mvSuccess.addObject("resp", resp);
		return true;
	}

	@Override
	public TxnResp doHKBAccountOpening(String userId, String openid) throws Exception {
		String productCode = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ACC_HKB_PROD_NO);
		String mchntCode = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ACC_HKB_MCHNT_NO);
		String insCode = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ACC_HKB_INS_CODE);
		
		return userMerchantAcctService.doCustomerAccountOpening(productCode, userId, openid, mchntCode, insCode);
	}

	
}
