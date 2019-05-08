package com.cn.thinkx.biz.salesactivity.service.impl;

import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.biz.core.service.CtrlSystemService;
import com.cn.thinkx.biz.mchnt.mapper.BizMchtMapper;
import com.cn.thinkx.biz.mchnt.model.MchtCommodities;
import com.cn.thinkx.biz.salesactivity.mapper.SalesActivityListMapper;
import com.cn.thinkx.biz.salesactivity.model.SalesActivityDetail;
import com.cn.thinkx.biz.salesactivity.model.SalesActivityList;
import com.cn.thinkx.biz.salesactivity.service.SalesActivityListService;
import com.cn.thinkx.biz.translog.model.IntfaceTransLog;
import com.cn.thinkx.biz.translog.service.IntfaceTransLogService;
import com.cn.thinkx.biz.user.mapper.BizUserMapper;
import com.cn.thinkx.dubbo.facadeImpl.txn.utils.HKBTxnUtil;
import com.cn.thinkx.facade.bean.RechargeTransRequest;
import com.cn.thinkx.itf.service.TxnSendMessageITF;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.service.MessageService;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransCode;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.connect.entity.BizMessageObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("salesActivityListService")
public class SalesActivityListServiceImpl implements SalesActivityListService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("salesActivityListMapper")
    private SalesActivityListMapper salesActivityListMapper;

    @Autowired
    @Qualifier("txnSendMessageITF")
    private TxnSendMessageITF txnSendMessageITF;

    @Autowired
    @Qualifier("bizMchtMapper")
    private BizMchtMapper bizMchtMapper;

    @Autowired
    @Qualifier("intfaceTransLogService")
    private IntfaceTransLogService intfaceTransLogService;

    @Autowired
    @Qualifier("ctrlSystemService")
    private CtrlSystemService ctrlSystemService;


    @Autowired
    @Qualifier("messageService")
    private MessageService messageService;

    @Autowired
    @Qualifier("bizUserMapper")
    private BizUserMapper bizUserMapper;

    /**
     * 当前商品是否参与营销活动
     *
     * @param commodityId 商品ID
     * @return
     */
    public SalesActivityDetail getSalesActivitByCommodityId(String commodityId) {
        return salesActivityListMapper.getSalesActivitByCommodityId(commodityId);
    }


    /**
     * 查找用户是购买商品剩余购买数量
     *
     * @param commodityId                   商品号
     * @param joinBody(tb_user_inf.USER_ID) 参与用户Id
     * @return
     */
    public int getCommodityStocksByCommodityId(String commodityId, String joinBody) {

        int commodityStocks = 0;
        try {
            MchtCommodities mchtCommodities = bizMchtMapper.getSellingCardStocksByCommodityId(commodityId);
            if (mchtCommodities != null) {
                List<SalesActivityDetail> salesActivityDetailList = this.getSalesActivityListCardStocks(mchtCommodities.getActiveId(), joinBody, commodityId); //查找营销活动的用户可购卡数量
                if (salesActivityDetailList != null && salesActivityDetailList.size() > 0) {
                    commodityStocks = salesActivityDetailList.get(0).getCommodityStocks();  //用户可购卡数量
                } else {
                    commodityStocks = Integer.parseInt(mchtCommodities.getCommodityStocks());
                }
            }
        } catch (Exception ex) {
            logger.error("查询商品库存失败 >" + ex);
        }
        return commodityStocks;
    }

    /**
     * 查找用户是购买商品剩余购买数量
     *
     * @param activityId  营销活动主键
     * @param commodityId 商品主键
     * @return
     * @Param userId 参与主体一般指当前用户ID
     */
    public int getSalesActivityCommodityNumByUserId(String activityId, String userId) {

        return salesActivityListMapper.getSalesActivityCommodityNumByUserId(activityId, userId);
    }

    /**
     * 保存用户参与活动明细
     *
     * @param salesActivityList
     * @return
     */
    public int saveSalesActivityList(SalesActivityList salesActivityList) {
        return salesActivityListMapper.insertSalesActivityList(salesActivityList);
    }

    /**
     * 修改用户参与活动明细
     *
     * @param salesActivityList
     * @return
     */
    public int updateSalesActivityList(SalesActivityList salesActivityList) {
        return salesActivityListMapper.updateSalesActivityList(salesActivityList);
    }

    /**
     * 查找用户是购买活动商品剩余购买数量
     *
     * @param activityBody 营销活动主体
     * @param commodityId  商品主键
     * @return
     * @Param joinBody 参与主体一般指当前用户ID
     */
    public List<SalesActivityDetail> getSalesActivityListCardStocks(String activityBody, String joinBody, String commodityId) {
        return salesActivityListMapper.getSalesActivityListCardStocks(activityBody, joinBody, commodityId);
    }

    /**
     * 充值 优惠活动
     *
     * @param req    充值请求参数
     * @param userId 参与用户
     * @param log    接口层日志
     */
    public void doRechargeSpecialActivity(final RechargeTransRequest req, final String userId, final IntfaceTransLog iftranslog) {

        final SalesActivityDetail salesActivityDetail = this.getSalesActivitByCommodityId(req.getCommodityCode());//是否有营销活动

//		//如果营销活动存在，购卡充值用户记录为空，则新增一条记录
        if (salesActivityDetail != null) {
            //已经参与该活动，购买商品的数量
            int buyCommodityNum = getSalesActivityCommodityNumByUserId(salesActivityDetail.getActivityId(), userId);

            if ((salesActivityDetail.getUserStockNum() - buyCommodityNum) <= 0) {

                return;  //如果活动允许次数
            }
            Thread rechargeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String activityListId = intfaceTransLogService.getPrimaryKey();
                    SalesActivityList salesActivityList = new SalesActivityList();

                    try {
                        salesActivityList = new SalesActivityList();
                        salesActivityList.setActivityListId(activityListId);
                        salesActivityList.setActivityDetailId(salesActivityDetail.getActivityDetailId());
                        salesActivityList.setActivityId(salesActivityDetail.getActivityId());
                        salesActivityList.setOrgInterfaceKey(iftranslog.getInterfacePrimaryKey());
                        salesActivityList.setJoinBody(userId);
                        salesActivityList.setTransDate(DateUtil.getCurrDate());
                        salesActivityList.setCardNum(Integer.parseInt(req.getCommodityNum()));
                        salesActivityList.setTransChnl(iftranslog.getTransChnl());
                        salesActivityList.setDataStat("0");
                        salesActivityList.setCreateUser("99999999");
                        salesActivityList.setUpdateUser("99999999");

                        int s1 = salesActivityListMapper.insertSalesActivityList(salesActivityList);

                        if (s1 != 1) {
                            logger.info("充值优惠活动交易--->插入活动记录失败，id[{}]", iftranslog.getInterfacePrimaryKey());
                            sendMessageByRechargeError(iftranslog.getInterfacePrimaryKey()); //发送短信
                            return;
                        }
                    } catch (Exception e) {
                        logger.error("充值优惠活动交易--->插入活动记录失败" + e);
                        return;
                    }

                    String transChnl = BaseConstants.RechargeSpecialChannelCode.findChannelCodeByOrgCode(req.getChannel()).getChannelCode();
                    CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
                    IntfaceTransLog log = new IntfaceTransLog();
                    if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入中间层流水
                        String intfaceTransLogId = intfaceTransLogService.getPrimaryKey();
                        log.setInterfacePrimaryKey(intfaceTransLogId);
                        log.setOrgInterfacePrimaryKey(iftranslog.getInterfacePrimaryKey());
                        log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
                        log.setSettleDate(cs.getSettleDate());// 交易日期
                        log.setDmsRelatedKey(activityListId);
                        log.setOrgDmsRelatedKey(iftranslog.getDmsRelatedKey());// 客户端传过来的流水号
                        log.setTransId(TransCode.CS20.getCode());// 交易类型
                        log.setTransSt(0);// 插入时为0，报文返回时更新为1
                        log.setInsCode(iftranslog.getInsCode());
                        log.setMchntCode(iftranslog.getMchntCode());
                        log.setShopCode(iftranslog.getShopCode());
                        log.setUserInfUserName(iftranslog.getUserInfUserName());
                        log.setTransAmt(salesActivityDetail.getActivityInfo());//
                        log.setUploadAmt(salesActivityDetail.getActivityInfo());// 上送金额
                        log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
                        log.setTransChnl(transChnl);// 客户端传过来的渠道号
                        log.setProductCode(iftranslog.getProductCode());
                        int s2 = intfaceTransLogService.insertIntfaceTransLog(log);// 中间层插入流水
                        if (s2 != 1) {
                            logger.info("充值优惠活动交易--->插入活动流水失败，充值流水号 id[{}]", iftranslog.getInterfacePrimaryKey());
                            sendMessageByRechargeError(iftranslog.getInterfacePrimaryKey()); //发送短信
                            return;
                        }

                        /***发送报文***/
                        BizMessageObj obj = null;
                        try {
                            obj = txnSendMessageITF.sendMessage(HKBTxnUtil.rechargeSpecialTrans(req, log.getInterfacePrimaryKey(), iftranslog.getInterfacePrimaryKey(), transChnl, log.getTransAmt()));
                            if (obj == null) {
                                sendMessageByRechargeError(log.getInterfacePrimaryKey()); //发送短信
                                logger.info("充值优惠活动交易--->交易返回报文为空，id[{}]", log.getInterfacePrimaryKey());
                            }
                        } catch (Exception e) {
                            logger.error("充值优惠活动交易--->发送报文是吧，id[{}]", log.getInterfacePrimaryKey());
                        }

                        /***更新流水***/
                        try {

                            log.setTransSt(1);// 插入时为0，报文返回时更新为1
                            log.setRespCode(obj.getRespCode());
                            int s3 = intfaceTransLogService.updateIntfaceTransLog(log);// 更新流水

                            if (s3 != 1) {
                                sendMessageByRechargeError(log.getInterfacePrimaryKey()); //发送短信
                                logger.info("充值优惠活动交易--->insertIntfaceTransLog更新流水失败，id[{}]", log.getInterfacePrimaryKey());
                            }
                        } catch (Exception e) {
                            logger.error("充值优惠活动交易--->更新流水失败，id[{}]", log.getInterfacePrimaryKey());
                        }

                        /***更新用户参加营销活动记录***/
                        try {
                            salesActivityList.setInterfaceKey(intfaceTransLogId);
                            salesActivityList.setRespCode(obj.getRespCode());

                            int s4 = salesActivityListMapper.updateSalesActivityList(salesActivityList);
                            if (s4 != 1) {
                                logger.info("充值优惠活动交易--->更新用户参加营销活动记录失败，id[{}]", log.getInterfacePrimaryKey());
                                sendMessageByRechargeError(log.getInterfacePrimaryKey()); //发送短信
                                return;
                            }

                        } catch (Exception e) {
                            logger.error("充值优惠活动交易--->更新用户参加营销活动记录失败，id[{}]", log.getInterfacePrimaryKey());
                        }

                        /***发送短信***/
                        try {
                            if (BaseConstants.RESPONSE_SUCCESS_CODE.equals(obj.getRespCode())) {
                                String phoneNo = bizUserMapper.getPhoneNoByUserId(userId);
                                String productName = bizMchtMapper.getProductNameByCode(iftranslog.getProductCode());
                                messageService.sendMessage(phoneNo, "【薪无忧】" + productName + "到账：" + NumberUtils.RMBCentToYuan(salesActivityDetail.getActivityInfo()) + "元，" + salesActivityDetail.getActivityDsp() + "，祝您生活愉快！");
                            } else {
                                logger.info("充值优惠活动交易--->用户参加营销活动记录失败，id[{}]", log.getInterfacePrimaryKey());
                                sendMessageByRechargeError(log.getInterfacePrimaryKey()); //发送短信
                            }

                        } catch (Exception e) {
                            logger.error("交易返利发送短信是吧" + e);
                        }
                    }
                }
            });
            rechargeThread.start();
        }
    }


    public void sendMessageByRechargeError(String interfacePrimaryKey) {
        String phonesStr = RedisDictProperties.getInstance().getdictValueByCode("SYSTEM_MONITORING_USER_PHONES");
        String[] users = phonesStr.split(",");
        if (users != null) {
            for (int i = 0; i < users.length; i++) {
                messageService.sendMessage(users[i], "【薪无忧】在处理返利业务时出现故障,流水号:" + interfacePrimaryKey + ",请及时处理!");
            }
        }
    }
}
