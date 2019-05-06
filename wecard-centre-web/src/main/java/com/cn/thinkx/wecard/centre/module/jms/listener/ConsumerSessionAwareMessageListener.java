package com.cn.thinkx.wecard.centre.module.jms.listener;

import com.cn.thinkx.common.activemq.domain.WechatCustomerParam;
import com.cn.thinkx.wechat.base.wxapi.process.MpAccount;
import com.cn.thinkx.wechat.base.wxapi.process.WxApiClient;
import net.sf.json.JSONObject;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Message;
import javax.jms.MessageListener;


/**
 * @描述: 队列监听器 .
 * @作者: zqy .
 * @创建时间: 2017-1-17,下午11:21:23
 * @版本号: V1.0
 */

public class ConsumerSessionAwareMessageListener implements MessageListener {
    private Logger logger = LoggerFactory.getLogger(ConsumerSessionAwareMessageListener.class);

    @Override
    public synchronized void onMessage(Message message) {
        try {
            ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
            final String ms = msg.getText();
            WechatCustomerParam consumerPatam = com.alibaba.fastjson.JSONObject.parseObject(ms, WechatCustomerParam.class);// 转换成相应的对象
            MpAccount mpAccount = WxApiClient.getMpAccount(consumerPatam.getAcountName());
            JSONObject result = WxApiClient.sendCustomTextMessage(consumerPatam.getToOpenId(), consumerPatam.getContent(), mpAccount); //发送客服消息

            if (result != null && result.getInt("errcode") == 0)
                message.acknowledge();

        } catch (Exception e) {
            logger.error("## 发送的客服消息异常：", e);
        }
    }
}