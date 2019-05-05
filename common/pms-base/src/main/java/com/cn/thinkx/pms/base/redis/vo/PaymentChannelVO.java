package com.cn.thinkx.pms.base.redis.vo;

import java.util.List;

public class PaymentChannelVO {

    private String id;    //通道_id
    private String channelNo;    //通道号
    private String channelName;    //通道名称
    private String rate;    //费率
    private String channelType;    //类型	0-9    2:第三方
    private String description;    //描述信息
    private String enable;    //启用标识	1：启用、0：禁用
    private String dataStat;    //状态
    private List<PaymentChannelApiVO> apiList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getDataStat() {
        return dataStat;
    }

    public void setDataStat(String dataStat) {
        this.dataStat = dataStat;
    }

    public List<PaymentChannelApiVO> getApiList() {
        return apiList;
    }

    public void setApiList(List<PaymentChannelApiVO> apiList) {
        this.apiList = apiList;
    }

}
