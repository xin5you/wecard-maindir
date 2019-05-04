package com.cn.thinkx.oms.module.enterpriseorder.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.activemq.service.WechatMQProducerService;
import com.cn.thinkx.common.redis.util.RedisDictProperties;
import com.cn.thinkx.oms.module.common.model.TxnResp;
import com.cn.thinkx.oms.module.customer.mapper.UserMerchantAcctMapper;
import com.cn.thinkx.oms.module.customer.model.AccountInf;
import com.cn.thinkx.oms.module.customer.model.PersonInf;
import com.cn.thinkx.oms.module.customer.model.UserMerchantAcct;
import com.cn.thinkx.oms.module.customer.service.AccountInfService;
import com.cn.thinkx.oms.module.customer.service.PersonInfService;
import com.cn.thinkx.oms.module.customer.service.UserMerchantAcctService;
import com.cn.thinkx.oms.module.enterpriseorder.mapper.BatchOrderListMapper;
import com.cn.thinkx.oms.module.enterpriseorder.mapper.BatchOrderMapper;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchOrder;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchOrderList;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchOrderService;
import com.cn.thinkx.oms.module.sys.mapper.UserMapper;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.DateUtils;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.service.MessageService;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelCode;
import com.cn.thinkx.pms.base.utils.BaseIntegrationPayConstants;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.wechat.base.wxapi.util.WXTemplateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("batchOrderService")
public class BatchOrderServiceImpl implements BatchOrderService {

	Logger logger = LoggerFactory.getLogger(BatchOrderServiceImpl.class);

	ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	@Autowired
	@Qualifier("batchOrderMapper")
	private BatchOrderMapper batchOrderMapper;

	@Autowired
	@Qualifier("batchOrderListMapper")
	private BatchOrderListMapper batchOrderListMapper;

	@Autowired
	@Qualifier("personInfService")
	private PersonInfService personInfService;

	@Autowired
	@Qualifier("userMerchantAcctService")
	private UserMerchantAcctService userMerchantAcctService;

	@Autowired
	@Qualifier("userMerchantAcctMapper")
	private UserMerchantAcctMapper userMerchantAcctMapper;

	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;

	@Autowired
	@Qualifier("messageService")
	private MessageService messageService;

	@Autowired
	@Qualifier("wechatMQProducerService")
	private WechatMQProducerService wechatMQProducerService;
	
	@Autowired
	@Qualifier("accountInfService")
	private AccountInfService accountInfService;

	@Override
	public List<BatchOrder> getBatchOrderList(BatchOrder order) {
		return batchOrderMapper.getBatchOrderList(order);
	}

	@Override
	public int addBatchOrder(BatchOrder order) {
		return batchOrderMapper.addBatchOrder(order);
	}

	@Override
	public int updateBatchOrder(BatchOrder order) {
		return batchOrderMapper.updateBatchOrder(order);
	}

	@Override
	public int deleteBatchOrder(String orderId) {
		return batchOrderMapper.deleteBatchOrder(orderId);
	}

	@Override
	public PageInfo<BatchOrder> getBatchOrderPage(int startNum, int pageSize, BatchOrder order,HttpServletRequest req) {
		PageHelper.startPage(startNum, pageSize);
		order.setOrderId(StringUtils.nullToString(req.getParameter("orderId")));
		order.setOrderName(StringUtils.nullToString(req.getParameter("orderName")));
		order.setOrderStat(StringUtils.nullToString(req.getParameter("orderStat")));
		order.setBizType(StringUtils.nullToString(req.getParameter("bizType")));
		order.setStartTime(StringUtils.nullToString(req.getParameter("startTime")));
		order.setEndTime(StringUtils.nullToString(req.getParameter("endTime")));
		List<BatchOrder> list = getBatchOrderList(order);
		for (BatchOrder batchOrder : list) {
			User user1 = userMapper.getUserById(batchOrder.getCreateUser());
			batchOrder.setCreateUser(user1.getLoginname());
			User user2 = userMapper.getUserById(batchOrder.getUpdateUser());
			batchOrder.setUpdateUser(user2.getLoginname());
			batchOrder.setOrderStat(BaseConstants.OMSOrderStat.findStat(batchOrder.getOrderStat()));
			batchOrder.setBizType(batchOrder.getBizType()==null?"":BaseConstants.OMSOrderBizType.findType(batchOrder.getBizType()));
			if (batchOrder.getSumAmount() == null || "".equals(batchOrder.getSumAmount())) {
				batchOrder.setSumAmount("" + NumberUtils.formatMoney(0));
			} else {
				batchOrder.setSumAmount("" + NumberUtils.formatMoney(batchOrder.getSumAmount()));
			}
		}
		PageInfo<BatchOrder> page = new PageInfo<BatchOrder>(list);
		return page;
	}

	@Override
	public int addBatchOrder(BatchOrder order, LinkedList<BatchOrderList> personInfList) {
		int a = 0;
		int b = 0;
		try {
			a = batchOrderMapper.addBatchOrder(order);
			for (BatchOrderList orderList : personInfList) {
				orderList.setOrderId(order.getOrderId());
				orderList.setProductCode(order.getProductCode());
				orderList.setProductName(order.getProductName());
				if (orderList.getAmount() != null && !"".equals(orderList.getAmount())) {
					orderList.setAmount(NumberUtils.RMBYuanToCent(orderList.getAmount()));
				}
				orderList.setOrderStat(BaseConstants.OMSOrderListStat.orderListStat_0.getCode());
				orderList.setCreateUser(order.getCreateUser());
				orderList.setUpdateUser(order.getCreateUser());
			}
			b = batchOrderListMapper.addBatchOrderList(personInfList);
			if (a > 0 && b > 0) {
				return 1;
			}
		} catch (Exception e) {
			logger.error("批量开户数据提交出错---->>{},a---->>{},b----->>{}", e.getMessage(), a, b);
		}
		return 0;
	}

	@Override
	public BatchOrder getBatchOrderByOrderId(String orderId) {
		return batchOrderMapper.getBatchOrderByOrderId(orderId);
	}

	@Override
	public BatchOrder getBatchOrderById(String orderId) {
		return batchOrderMapper.getBatchOrderById(orderId);
	}

	/**
	 * 批量开户
	 * 
	 * @param orderId
	 *            订单ID
	 */
	public void batchOpenAccountITF(final String orderId, User user, final String commitStat) {

		BatchOrder order = batchOrderMapper.getBatchOrderById(orderId);
		if (order != null && BaseConstants.OMSOrderStat.orderStat_10.getCode().equals(order.getOrderStat())) {
			order.setOrderStat(BaseConstants.OMSOrderStat.orderStat_20.getCode());
			order.setUpdateUser(user.getId().toString());
			this.updateBatchOrder(order); // 修改订单状态
		}
		// 批量开户
		try {
			cachedThreadPool.execute(new Runnable() {
				public void run() {
					BatchOrder bo;
					boolean operErrorFlag = false;
					try {
						bo = batchOrderMapper.getBatchOrderById(orderId);
						bo.setOrderStat(BaseConstants.OMSOrderStat.orderStat_30.getCode());
						batchOrderMapper.updateBatchOrder(bo);
						List<BatchOrderList> list = getBatchOrderList(orderId, commitStat);
						PersonInf personInf;
						for (BatchOrderList order : list) {
							personInf = new PersonInf();
							personInf.setPersonalName(order.getUserName());
							personInf.setMobilePhoneNo(order.getPhoneNo());
							personInf.setPersonalCardType(BaseConstants.CardTypeEnum.CARD_TYPE_00.getCode());
							personInf.setPersonalCardNo(order.getUserCardNo());
							try {
								int operNum = personInfService.doPersonInfRegister(personInf);// 用户注册
								if (operNum > 0) {
									order.setOrderStat(BaseConstants.OMSOrderListStat.orderListStat_00.getCode());
									batchOrderListMapper.updateBatchOrderList(order);
								} else {
									operErrorFlag = true;
									order.setOrderStat(BaseConstants.OMSOrderListStat.orderListStat_99.getCode());
									batchOrderListMapper.updateBatchOrderList(order);
								}
							} catch (Exception ex) {
								operErrorFlag = true;
								logger.error("批量开户doPersonInfRegister", ex);
							}
						}
						if (!operErrorFlag) {
							bo.setOrderStat(BaseConstants.OMSOrderStat.orderStat_40.getCode());
						} else {
							bo.setOrderStat(BaseConstants.OMSOrderStat.orderStat_90.getCode());
						}
						batchOrderMapper.updateBatchOrder(bo);
					} catch (Exception ex) {
						logger.error("批量开户 异常", ex);
					}
				};
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量开card
	 * 
	 * @param orderId
	 *            订单ID
	 */
	public void batchOpenCardITF(final String orderId, User user, final String commitStat) {
		BatchOrder order = batchOrderMapper.getBatchOrderById(orderId);
		if (order != null && BaseConstants.OMSOrderStat.orderStat_10.getCode().equals(order.getOrderStat())) {
			order.setOrderStat(BaseConstants.OMSOrderStat.orderStat_20.getCode());
			order.setUpdateUser(user.getId().toString());
			this.updateBatchOrder(order); // 修改订单状态
		}
		// 批量开card
		try {
			cachedThreadPool.execute(new Runnable() {
				public void run() {
					BatchOrder bo;
					boolean operErrorFlag = false;

					bo = batchOrderMapper.getBatchOrderById(orderId);
					bo.setOrderStat(BaseConstants.OMSOrderStat.orderStat_30.getCode());
					batchOrderMapper.updateBatchOrder(bo);
					List<BatchOrderList> list = getBatchOrderList(orderId, commitStat);
					PersonInf personInf;

					for (BatchOrderList order : list) {
						// 检查用户是否开户
						personInf = personInfService.getPersonInfByPhoneAndChnl(order.getPhoneNo(),
								ChannelCode.CHANNEL0.toString());
						TxnResp resp = new TxnResp();
						try {
							if (personInf != null) {
								// 单个用户开卡
								resp = userMerchantAcctService.doCustomerCardOpeningITF(order.getOrderListId(),
										personInf.getUserId(), personInf.getPersonalId(), bo.getProductCode());
							} else {
								resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
								resp.setInfo("通过手机号未查找到用户信息");
							}
						} catch (Exception ex) {
							resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
							resp.setInfo("开卡异常");
							logger.error("开卡doCustomerCardOpeningITF异常{}", ex);
						}

						try {
							// 成功或失败的状态更新
							if (BaseConstants.RESPONSE_SUCCESS_CODE.equals(resp.getCode())
									|| BaseConstants.ITFRespCode.CODE94.getCode().equals(resp.getCode())) {
								order.setOrderStat(BaseConstants.OMSOrderListStat.orderListStat_00.getCode());
								order.setRemarks(resp.getInfo());
								batchOrderListMapper.updateBatchOrderList(order);
							} else {
								operErrorFlag = true;
								order.setOrderStat(BaseConstants.OMSOrderListStat.orderListStat_99.getCode());
								order.setRemarks(resp.getInfo());
								batchOrderListMapper.updateBatchOrderList(order);
							}
						} catch (Exception ex) {
							logger.error("修改批量订单明显异常{}", ex);
						}
					}

					try {
						if (!operErrorFlag) {
							bo.setOrderStat(BaseConstants.OMSOrderStat.orderStat_40.getCode());
						} else {
							bo.setOrderStat(BaseConstants.OMSOrderStat.orderStat_90.getCode());
						}
						batchOrderMapper.updateBatchOrder(bo);
					} catch (Exception ex) {
						logger.error("修改批量订单状态异常{}", ex);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量充值
	 * @param orderId 订单
	 * @param user 用户 
	 */
	public void batchRechargeCardITF(final String orderId,User user,final String commitStat){

		BatchOrder order = batchOrderMapper.getBatchOrderById(orderId);
		if (order != null && BaseConstants.OMSOrderStat.orderStat_10.getCode().equals(order.getOrderStat())) {
			order.setOrderStat(BaseConstants.OMSOrderStat.orderStat_20.getCode());
			order.setUpdateUser(user.getId().toString());
			this.updateBatchOrder(order); // 修改订单状态
		}
		// 批量充值
		 try {
			 cachedThreadPool.execute(new Runnable() {
					public void run() {
						BatchOrder bo;
						boolean operErrorFlag = false;

						bo = batchOrderMapper.getBatchOrderById(orderId);
						bo.setOrderStat(BaseConstants.OMSOrderStat.orderStat_30.getCode());
						batchOrderMapper.updateBatchOrder(bo);
						List<BatchOrderList> list = getBatchOrderList(orderId, commitStat);
						PersonInf personInf;

						// 查找卡产品信息
						UserMerchantAcct userMerchantAcct = new UserMerchantAcct();
						userMerchantAcct.setProductCode(bo.getProductCode());
						boolean merchantacctflag = false;
						if(RedisDictProperties.getInstance().getdictValueByCode("ACC_HKB_PROD_NO").equals(bo.getProductCode())){
							userMerchantAcct.setInsCode(RedisDictProperties.getInstance().getdictValueByCode("ACC_HKB_INS_CODE"));
							userMerchantAcct.setMchntCode(RedisDictProperties.getInstance().getdictValueByCode("ACC_HKB_MCHNT_NO"));
							merchantacctflag = true;
						}else{
						// 获取商户下所有的卡
							List<UserMerchantAcct> productlist = userMerchantAcctService.getMerchantCardByMchnt(userMerchantAcct);
							userMerchantAcct = productlist.get(0);
						}
						for (BatchOrderList order : list) {
							// 检查用户是否开卡
							personInf = personInfService.getPersonInfByPhoneAndChnl(order.getPhoneNo(),
									ChannelCode.CHANNEL0.toString());

							TxnResp resp = new TxnResp();
							try {
								if (personInf != null) {
									// 单个用户充值
									resp = userMerchantAcctService.doCustomerRechargeTransactionITF(order.getOrderListId(),
											personInf.getUserId(), personInf.getExternalId(), order.getAmount(),
											userMerchantAcct.getMchntCode(), userMerchantAcct.getInsCode());
								} else {
									resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
									resp.setInfo("通过手机号未查找到用户信息");
								}
							} catch (Exception ex) {
								resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
								resp.setInfo("开卡异常");
								logger.error("开卡doCustomerCardOpeningITF异常{}", ex);
							}
							try {
								// 成功或失败的状态更新
								if (BaseConstants.RESPONSE_SUCCESS_CODE.equals(resp.getCode())
										|| BaseConstants.ITFRespCode.CODE94.getCode().equals(resp.getCode())) {
									order.setOrderStat(BaseConstants.OMSOrderListStat.orderListStat_00.getCode());
									order.setRemarks(resp.getInfo());
									batchOrderListMapper.updateBatchOrderList(order);
									if(RedisDictProperties.getInstance().getdictValueByCode("ACC_HKB_PROD_NO").equals(userMerchantAcct.getProductCode())){
										/*boolean sendStatus = messageService.sendMessage(order.getPhoneNo(), "【知了企服】尊敬的用户，"+bo.getCompanyName()+"于"+DateUtils.getDate(order.getCreateTime(), "yyyy年MM月dd日")
										+ "为您"+NumberUtils.hiddingMobileNo(order.getPhoneNo())+"的知了企服余额充值"+NumberUtils.RMBCentToYuan(order.getAmount())+"元，请进入知了企服微信公众号\"我的\"-“个人中心”查看，"
										+ "如有疑问请致电客服021-64189869");*/
										String templateCode = RedisDictProperties.getInstance().getdictValueByCode("ALIYUN_MSM_TEMPLATE_CODE_RECHARGE");
										String templateParam = "{\"company\":\"知了企服\", \"amount\":\"" + NumberUtils.RMBCentToYuan(order.getAmount()) + "\"}";
										boolean sendStatus = messageService.sendMessage(order.getPhoneNo(), templateCode, templateParam);
										if (sendStatus) {
											
										} else {
											logger.error("## 手机号[{}]短信发送失败", order.getPhoneNo());
										}
									}
									//平台充值的模板推送消息
									String openId = batchOrderMapper.getOpenIdByPhoneNo(order.getPhoneNo());
									if(openId != null && openId != ""){
										UserMerchantAcct acc = new UserMerchantAcct();
										acc.setExternalId(openId);
										acc.setMchntCode(userMerchantAcct.getMchntCode());
										acc.setUserId(personInf.getUserId());
										List<UserMerchantAcct> cardList = userMerchantAcctService.getUserMerchantAcctByUser(acc);
										if (cardList != null && cardList.size() > 0) {
											acc = cardList.get(0);
										}
										String channelName = BaseIntegrationPayConstants.OMSChannelCode.findOMSChannelCodeByCode("10001001");
										String customerAcount = RedisDictProperties.getInstance().getdictValueByCode("WX_CUSTOMER_ACCOUNT");	//获取知了企服公众号
										String desc = "";	//描述
										if(merchantacctflag){
											desc = bo.getCompanyName()+"已为您知了企服余额成功充值\n";
											userMerchantAcct.setMchntName("知了企服余额");
										}else{
											desc = "您已成功购卡充值\n";
										}
										/**        发送模板消息                */
										wechatMQProducerService.sendTemplateMsg(customerAcount, openId, "WX_TEMPLATE_ID_3", null, WXTemplateUtil.setPurchaseData(userMerchantAcct.getMchntName(),channelName,desc,DateUtils.getDate(order.getCreateTime(), "yyyy-MM-dd HH:mm:ss"), NumberUtils.RMBCentToYuan(order.getAmount()), NumberUtils.RMBCentToYuan(acc.getAccBal())));
									}
								} else {
									operErrorFlag = true;
									order.setOrderStat(BaseConstants.OMSOrderListStat.orderListStat_99.getCode());
									order.setRemarks(resp.getInfo());
									batchOrderListMapper.updateBatchOrderList(order);
								}
							} catch (Exception ex) {
								logger.error("修改批量订单明显异常{}", ex);
							}
						}
						try {
							if (!operErrorFlag) {
								bo.setOrderStat(BaseConstants.OMSOrderStat.orderStat_40.getCode());
							} else {
								bo.setOrderStat(BaseConstants.OMSOrderStat.orderStat_90.getCode());
							}
							batchOrderMapper.updateBatchOrder(bo);
						} catch (Exception ex) {
							logger.error("修改批量订单状态异常{}", ex);
						}
					}
				});
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

	public List<BatchOrderList> getBatchOrderList(String orderId, String commitStat) {
		List<BatchOrderList> list = null;
		if (BaseConstants.OMSOrderStat.orderStat_10.getCode().equals(commitStat)) {
			list = batchOrderListMapper.getBatchOrderListList(orderId);
		}
		if (BaseConstants.OMSOrderStat.orderStat_90.getCode().equals(commitStat)) {
			list = batchOrderListMapper.getBatchOrderListFailList(orderId);
		}
		return list;
	}

	public void batchUpdateQuotaITF(String orderId, String maxAmt, User user) {
		
		try {
			List<BatchOrderList> list = batchOrderListMapper.getBatchOrderListList(orderId);
			PersonInf personInf;
			UserMerchantAcct userMerchantAcct;
			AccountInf account;
			for (BatchOrderList orderList : list) {
				// 检查用户是否开卡
				personInf = personInfService.getPersonInfByPhoneAndChnl(orderList.getPhoneNo(),
						ChannelCode.CHANNEL0.toString());
				if(personInf != null){
					
					userMerchantAcct = new UserMerchantAcct();
					userMerchantAcct.setUserId(personInf.getUserId());
					userMerchantAcct.setProductCode(orderList.getProductCode());
					List<UserMerchantAcct> userMerchantAcctList = userMerchantAcctMapper.getUserMerchantAcctByUser(userMerchantAcct);
					
					account = new AccountInf();
					account.setMaxTxnAmt2(maxAmt);
					account.setMaxDayTxnAmt2(maxAmt);
					account.setAccountNo(userMerchantAcctList.get(0).getAccountNo());
					account.setUserId(userMerchantAcctList.get(0).getUserId());
					account.setUpdateUser(user.getId().toString());
					accountInfService.updateAccountInf(account);
					
				} else {
					logger.info("通过手机号未查找到用户信息 ,手机号码 -------------->>",orderList.getPhoneNo());
				}
			}
			
		} catch (Exception e) {
			logger.error("批量修改网上交易限额异常{}", e);
		}
	}
}
