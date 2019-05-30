package com.cn.thinkx.pms.base.utils;

public class BaseConstants {


    // 报文消息头
    public static final String MSG_HEAD = "MSG_HEAD";
    // 通信成功后返回值
    public static final String RESPONSE_SUCCESS_CODE = "00";

    public static final String RESPONSE_SUCCESS_INFO = "交易成功";
    // 出现硬件或者网络等其他因素导致的失败交易返回码
    public static final String TXN_TRANS_ERROR = "99";
    public static final String RESPONSE_EXCEPTION_CODE = "96";

    public static final String WX_EXCEPTION_CODE = "990";
    public static final String WX_EXCEPTION_INFO = "请从微信平台访问";

    public static final String RESPONSE_EXCEPTION_INFO = "网络异常,请稍后再试";
    public static final String RESPONSE_REPEAT_TRANS = "重复交易";
    public static final String RESPONSE_COMMAMOUNT_ERROR = "购卡与实际交易金额不一致";
    // 日切允许状态
    public static final String TRANS_FLAG_YES = "1";
    //货币类型
    public static final String TRANS_CURR_CD = "156";
    // 定义成功值
    public static final String SUCCESS = "success";
    // 定义失败值
    public static final String FAILED = "failed";

    //薪无忧返回交易成功状态
    public static final String HKB_SUCCESS = "SUCCESS";

    //薪无忧返回交易失败状态
    public static final String HKB_FAIL = "FAIL";

    // 交易核心返回状态：成功
    public static final String TXN_TRANS_RESP_SUCCESS = "00";

    // 交易核心返回状态：无效卡号包括销毁状态
    public static final String TXN_TRANS_RESP_INVALID_CARD = "14";


    // 微信端注册 查询密码
    public static final String PWD_PINQUIRY = "0000000000000000";

    // 微信端管理员权限session名
    public static final String MANAGER_RESOURCES_SESSION = "manager_resources_session";

    // 薪无忧通卡信息
    public static final String ACC_HKB_MCHNT_NO = "ACC_HKB_MCHNT_NO";
    public static final String ACC_HKB_PROD_NO = "ACC_HKB_PROD_NO";
    public static final String ACC_HKB_INS_CODE = "ACC_HKB_INS_CODE";
    public static final String ACC_HKB_SHOP_NO = "ACC_HKB_SHOP_NO";
    public static final String ACC_ITF = "ACC_ITF";
    public static final String ACC_HKB = "ACC_HKB";

    // 薪无忧工资卡信息
    public static final String WAGES_XIN5YOU_MCHNT_NO = "WAGES_XIN5YOU_MCHNT_NO";
    public static final String WAGES_XIN5YOU_PROD_NO = "WAGES_XIN5YOU_PROD_NO";
    public static final String WAGES_XIN5YOU_INS_CODE = "WAGES_XIN5YOU_INS_CODE";
    public static final String WAGES_XIN5YOU_SHOP_NO = "WAGES_XIN5YOU_SHOP_NO";

    // 薪无忧工资卡密商品
    public static final String CARD_WAGES_XIN5YOU_PROD_NO = "CARD_WAGES_XIN5YOU_PROD_NO";

    public static final String PHONE_RECHARGE_REQ_KEY = "PHONE_RECHARGE_REQ_KEY";
    public static final String PHONE_RECHARGE_REQ_REDIRECT_URL = "PHONE_RECHARGE_REQ_REDIRECT_URL";
    public static final String PHONE_RECHARGE_REQ_NOTIFY_URL = "PHONE_RECHARGE_REQ_NOTIFY_URL";
    public static final String PHONE_RECHARGE_REQ_URL = "PHONE_RECHARGE_REQ_URL";
    public static final String GET_PHONE_INFO_URL = "GET_PHONE_INFO_URL";
    public static final String PHONE_RECHARGE_ALL_GOODS = "PHONE_RECHARGE_ALL_GOODS";
    public static final String PHONE_RECHARGE_YD_GOODS = "PHONE_RECHARGE_YD_GOODS";
    public static final String PHONE_RECHARGE_LT_GOODS = "PHONE_RECHARGE_LT_GOODS";
    public static final String PHONE_RECHARGE_DX_GOODS = "PHONE_RECHARGE_DX_GOODS";


    //薪无忧商城url
    public static final String ESHOP_HKB_ECOM_STORE_URL = "ESHOP_HKB_ECOM_STORE_URL";

    /**
     * 嘉福（京东，美团，大众点评）
     */
    public static final String ACC_HKB_JD_MD5_KEY = "ACC_HKB_JD_MD5_KEY";
    public static final String ACC_HKB_JD_URL = "ACC_HKB_JD_URL";
    public static final String IDENT = "hkb";//接入识别码
    public static final String E_EID = "hkb_001";// 企业标识
    public static final String JINGDONG_SERVICE = "h5.scene.ds.master";//京东电商平台主页（服务场景）
    public static final String MEITUAN_SERVICE = "h5.scene.dianping";//美团电商平台主页（服务场景）
    public static final String DIANPING_SERVICE = "h5.scene.dianping";//点评电商平台主页（服务场景）
    public static final String ORDER_SERVICE = "h5.scene.my.order";//京东电商平台订单详情

    /**
     * 海豚通通兑 - 加密密钥
     */
    public static final String HTTTD_3DES_KEY = "HTTTD_3DES_KEY";
    /**
     * 海豚通通兑 - 获取海豚通通兑进入商城前获取参数链接
     */
    public static final String HTTTD_VALUES_URL = "HTTTD_VALUES_URL";
    /**
     * 海豚通通兑 - 跳转海豚通通兑商城链接
     */
    public static final String HTTTD_HTTP_URL = "HTTTD_HTTP_URL";
    /**
     * 海豚通通兑 - 海豚通通兑appId参数
     */
    public static final String HTTTD_APPID = "HTTTD_APPID";
    /**
     * 海豚通通兑 - 海豚通通兑机构号
     */
    public static final String HTTTD_INSTITUTIONNO = "HTTTD_INSTITUTIONNO";

    /**
     * 鼎驰 - 请求链接
     */
    public static final String DINGCHI_HTTP_URL = "DINGCHI_HTTP_URL";
    /**
     * 鼎驰 - 购买地址Url
     */
    public static final String DINGCHI_BUY_URL = "DINGCHI_BUY_URL";
    /**
     * 鼎驰 - 订单查询地址Url
     */
    public static final String DINGCHI_QUERY_URL = "DINGCHI_QUERY_URL";
    /**
     * 鼎驰 - 余额查询地址Url
     */
    public static final String DINGCHI_QUERYBAL_URL = "DINGCHI_QUERYBAL_URL";

    /**
     * 薪无忧卡券集市  - 购买卡券异步回调链接
     */
    public static final String HKB_WELFAREMART_BUYCARD_NOTIFY_URL = "HKB_WELFAREMART_BUYCARD_NOTIFY_URL";
    /**
     * 薪无忧卡券集市  - 购买卡券重定向链接
     */
    public static final String HKB_WELFAREMART_REDIRECT_URL = "HKB_WELFAREMART_REDIRECT_URL";
    /**
     * 薪无忧卡券集市  - 转让卡券链接
     */
    public static final String HKB_WELFAREMART_RESELL_URL = "HKB_WELFAREMART_RESELL_URL";
    /**
     * 薪无忧卡券集市  - 转让卡券异步回调链接
     */
    public static final String HKB_WELFAREMART_RESELL_NOTIFY_URL = "HKB_WELFAREMART_RESELL_NOTIFY_URL";
    /**
     * 薪无忧卡券集市  - 转让卡券接口加密KEy
     */
    public static final String WELFAREMART_RESELL_KEY = "WELFAREMART_RESELL_KEY";
    /**
     * 薪无忧卡券集市  - 卡券代付接口加密KEy
     */
    public static final String WELFAREMART_WITHDRAW_KEY = "WELFAREMART_WITHDRAW_KEY";
    /**
     * 薪无忧卡券集市  - 卡券充值地址
     */
    public static final String WELFAREMART_RECHARGE_REQUEST_URL = "WELFAREMART_RECHARGE_REQUEST_URL";
    /**
     * 薪无忧卡券集市  - 卡券充值异步回调地址
     */
    public static final String WELFAREMART_RECHARGE_NOTIFY_URL = "WELFAREMART_RECHARGE_NOTIFY_URL";

    /**
     * 薪无忧话费充值，查询话费充值状态URL
     */
    public static final String WELFAREMART_GET_RECHARGE_STATE_URL = "WELFAREMART_GET_RECHARGE_STATE_URL";

    /**
     * 批量代付请求地址 - 薪无忧
     */
    public static final String WITHDRAW_REQUEST_URL = "WITHDRAW_REQUEST_URL";
    /**
     * 批量代付请求地址 - 回调
     */
    public static final String WITHDRAW_NOTIFY_URL = "WITHDRAW_NOTIFY_URL";
    /**
     * 批量代付请求地址 - 苏宁易付宝
     */
    public static final String WITHDRAW_YFB_URL = "WITHDRAW_YFB_URL";
    /**
     * 批量代付查询请求地址 - 苏宁易付宝
     */
    public static final String WITHDRAW_QUERY_YFB_URL = "WITHDRAW_QUERY_YFB_URL";

    /**
     * 话费充值-立方 （front）请求地址
     */
    public static final String PHONE_RECHARGE_FRONT_REQUEST_URL = "PHONE_RECHARGE_FRONT_REQUEST_URL";
    /**
     * 薪无忧手机充值（流量）  - 异步回调地址
     */
    public static final String FLOW_RECHARGE_NOTIFY_URL = "FLOW_RECHARGE_NOTIFY_URL";
    /**
     * 薪无忧手机充值  - 重定向地址
     */
    public static final String PHONE_RECHARGE_REDIRECT_URL = "PHONE_RECHARGE_REDIRECT_URL";
    /**
     * 话费充值-立方   - access_token
     */
    public static final String BM_ACCESS_TOKEN = "BM_ACCESS_TOKEN";

    /***
     * 话费充值，默认的路由 redis 存储KEY
     */
    public static final String TELE_PROVIDER_IS_DEFAULT = "TELE_PROVIDER_IS_DEFAULT";

    //手机充值退款加密key
//	public static final String PHONE_RECHARGE_REQ_KEY = "PHONE_RECHARGE_REQ_KEY";
    public static final String PHONE_RECHARGE_REFUND = "TRANS_ORDER_REFUND";

    /**
     * 阿里云短信模板code-注册
     */
    public static final String ALIYUN_MSM_TEMPLATE_CODE_REGISTER = "ALIYUN_MSM_TEMPLATE_CODE_REGISTER";
    /**
     * 阿里云短信模板code-密码重置
     */
    public static final String ALIYUN_MSM_TEMPLATE_CODE_PWDRESET = "ALIYUN_MSM_TEMPLATE_CODE_PWDRESET";
    /**
     * 阿里云短信模板code-卡券转让
     */
    public static final String ALIYUN_MSM_TEMPLATE_CODE_CARDRESELL = "ALIYUN_MSM_TEMPLATE_CODE_CARDRESELL";
    /**
     * 阿里云短信模板code-银行卡添加
     */
    public static final String ALIYUN_MSM_TEMPLATE_CODE_ADDBANKCARD = "ALIYUN_MSM_TEMPLATE_CODE_ADDBANKCARD";
    /**
     * 阿里云短信模板code-充值
     */
    public static final String ALIYUN_MSM_TEMPLATE_CODE_RECHARGE = "ALIYUN_MSM_TEMPLATE_CODE_RECHARGE";
    /**
     * 阿里云短信模板参数productt
     */
    public static final String ALIYUN_MSM_PRODUCT = "ALIYUN_MSM_PRODUCT";
    /**
     * 阿里云短信模板参数domain
     */
    public static final String ALIYUN_MSM_DOMAIN = "ALIYUN_MSM_DOMAIN";
    /**
     * 阿里云短信模板参数accesskeyId
     */
    public static final String ALIYUN_MSM_ACCESSKEYID = "ALIYUN_MSM_ACCESSKEYID";
    /**
     * 阿里云短信模板参数accesskeySecret
     */
    public static final String ALIYUN_MSM_ACCESSKEYSECRET = "ALIYUN_MSM_ACCESSKEYSECRET";
    /**
     * 阿里云短信模板参数signName
     */
    public static final String ALIYUN_MSM_SIGNNAME = "ALIYUN_MSM_SIGNNAME";

    /**
     * 转让费率
     */
    public static final String RESELL_FEE = "3";


    /**
     * 薪无忧工资余额  - 购卡提现异步回调链接
     */
    public static final String XIN5YOU_WELFAREMART_BALANCEDRAW_NOTIFY_URL = "XIN5YOU_WELFAREMART_BALANCEDRAW_NOTIFY_URL";
    /**
     * 薪无忧工资余额  - 购卡提现重定向链接
     */
    public static final String XIN5YOU_WELFAREMART_BALANCEDRAW_REDIRECT_URL = "XIN5YOU_WELFAREMART_BALANCEDRAW_REDIRECT_URL";

    /**
     * 电商商城渠道号
     *
     * @author xiaomei
     */
    public enum ChannelEcomType {
        CEU01("40006001", "海易通商城"),
        CEU02("40006002", "家乐宝商城"),
        CEU03("40006003", "京东商城"),
        CEU04("40006004", "美团"),
        CEU05("40006005", "大众点评"),
        CEU06("40006006", "自营商城"),
        CEU07("40006007", "海豚通商城"),
        CEU08("40006008", "手机充值"),
        CEU09("40006009", "卡券集市");
        private String code;
        private String value;

        ChannelEcomType(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static ChannelEcomType findByCode(String code) {
            for (ChannelEcomType t : ChannelEcomType.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }

    /**
     * 订单异步通知结果
     */
    public enum TransOrderResult {
        FAIL("FAIL"),
        SUCCESS("SUCCESS");

        private String value;

        TransOrderResult(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * oms管理平台通卡的渠道类型
     *
     * @author kpplg
     */
    public enum OMSChannel {
        CHANNEL1001("1001", "薪无忧", "40001001,40001010,40001002,40002001,50002001,40007001,40008001"),// 薪无忧
        CHANNEL4001("4001", "嘉福", "40004001,50004001"),// 嘉福
        CHANNEL6001("6001", "电商", "40006001"),// 电商
        CHANNEL10001("10001", "平台", "10001001");// 平台
        private String code;
        private String name;
        private String value;

        OMSChannel(String code, String name, String value) {
            this.code = code;
            this.name = name;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public static OMSChannel findByCode(String code) {
            for (OMSChannel t : OMSChannel.values()) {
                if (t.getCode().equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }

        public static String contansValue(String value) {
            for (OMSChannel t : OMSChannel.values()) {
                if (t.getValue().contains(value)) {
                    return t.getName();
                }
            }
            return null;
        }

    }

    public enum OMSShopType {
        shopTYpe10("10", "实体门店"),
        shopTYpe20("20", "商城门店"),
        shopTYpe30("30", "虚拟门店"),
        shopTYpe99("99", "其他");
        private String code;
        private String name;

        OMSShopType(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public enum OMSSellCardFlag {
        sellCardFlag0("0", "允许售卡"),
        sellCardFlag1("1", "不允许售卡");

        private String code;
        private String name;

        OMSSellCardFlag(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public enum OMSOrderStat {
        orderStat_10("10", "草稿"),
        orderStat_19("19", "取消"),
        orderStat_20("20", "待处理"),
        orderStat_30("30", "处理中"),
        orderStat_40("40", "处理成功"),
        orderStat_90("90", "部分成功");

        private String code;
        private String stat;

        OMSOrderStat(String code, String stat) {
            this.code = code;
            this.stat = stat;
        }

        public static String findStat(String code) {
            for (OMSOrderStat t : OMSOrderStat.values()) {
                if (t.getCode().contains(code)) {
                    return t.getStat();
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }


    }

    public enum OMSOrderType {
        orderType_100("100", "批量开户"),
        orderType_200("200", "批量开卡"),
        orderType_300("300", "批量充值");

        private String code;
        private String type;

        OMSOrderType(String code, String type) {
            this.code = code;
            this.type = type;
        }

        public static String findType(String code) {
            for (OMSOrderType t : OMSOrderType.values()) {
                if (t.getCode().contains(code)) {
                    return t.getType();
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }

    public enum OMSOrderBizType {
        orderRechargeType10("10", "返利"),
        orderRechargeType13("13", "福利充值"),
        orderRechargeType12("12", "账户更改"),
        orderRechargeType90("90", "电商退款"),
        orderRechargeType91("91", "手机充值退款");

        private String code;
        private String type;

        OMSOrderBizType(String code, String type) {
            this.code = code;
            this.type = type;
        }

        public static String findType(String code) {
            for (OMSOrderBizType t : OMSOrderBizType.values()) {
                if (t.getCode().contains(code)) {
                    return t.getType();
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }


    }

    public enum OMSOrderListStat {
        orderListStat_0("0", "未处理"),
        orderListStat_00("00", "处理成功"),
        orderListStat_99("99", "处理失败");

        private String code;
        private String stat;

        OMSOrderListStat(String code, String stat) {
            this.code = code;
            this.stat = stat;
        }

        public static String findOrderListStat(String code) {
            for (OMSOrderListStat t : OMSOrderListStat.values()) {
                if (t.getCode().contains(code)) {
                    return t.getStat();
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }


    }

    /**
     * 规则类型信息表
     */
    public enum SendMsgTypeEnum {
        msg_01("01", "注册"),
        msg_02("02", "账户设置"),
        msg_03("03", "消费退款"),
        msg_04("04", "卡券转让"),
        msg_05("05", "银行卡添加"),
        msg_NUll("100", "");
        private String code;

        private String name;

        SendMsgTypeEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public static SendMsgTypeEnum findByCode(String code) {
            for (SendMsgTypeEnum value : SendMsgTypeEnum.values()) {
                if (value.code.equals(code)) {
                    return value;
                }
            }
            return SendMsgTypeEnum.msg_NUll;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 规则类型信息表
     */
    public enum RuleTypeEnum {
        RULETYPE_1000("1000", "百分比"),
        RULETYPE_2000("2000", "万分比");

        private String code;

        private String name;

        RuleTypeEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 商户账户信息表  账户类型
     */
    public enum MchntAcctTypeEnum {
        DEPOSIT_MONEY_ACCT_TYPE("100", "沉淀资金账户"),
        WECHAT_ACCT_TYPE("200", "微信充值账户"),
        ALIPAY_ACCT_TYPE("300", "支付宝充值账户"),
        JIAFU_ACCT_TYPE("400", "嘉福平台"),
        E_BANK_ACCT_TYPE("500", "网银账户");

        private String code;

        private String name;

        MchntAcctTypeEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 个人信息表  证件类型
     */
    public enum CardTypeEnum {
        CARD_TYPE_00("00", "身份证"),
        CARD_TYPE_10("10", "军官证"),
        CARD_TYPE_20("20", "护照"),
        CARD_TYPE_30("30", "学生证");
        private String code;

        private String name;

        CardTypeEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }


    /**
     * 商户账户信息表 账户状态
     */
    public enum MchntAcctStatEnum {
        NORMAL_ACCT_SATA("00", "有效"),
        CANCELLATION_ACCT_SATA("10", "注销"),
        FROZEN_ACCT_SATA("20", "冻结");
        private String code;

        private String name;

        MchntAcctStatEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }


    /**
     * 公账号分组 0,1,2系统默认 第一次商户关注状态为 0
     */
    public enum GroupsIdStatEnum {
        groupdefauls_stat("0");

        private String code;

        GroupsIdStatEnum(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     * 用户操作菜单状态  00-管理员分享角色
     */
    public enum ShareTypeEnum {
        SHARE_MANAGER_ROLE("00");

        private String code;

        ShareTypeEnum(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }


    /**
     * 用户操作菜单状态  00-无权限；10-获得权限
     */
    public enum FansStatusEnum {
        Fans_STATUS_00("00"),
        Fans_STATUS_10("10");

        private String code;

        FansStatusEnum(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     * 商户粉丝用户状态 1-可用；0-不可用
     */
    public enum AccountFansStatusEnum {
        TRUE_STATUS(1),
        FALSE_STATUS(0);

        private Integer code;

        AccountFansStatusEnum(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }

    /**
     * 数据启用标记
     *
     * @author 13501
     */
    public enum DataStatEnum {

        TRUE_STATUS("0"),
        FALSE_STATUS("1");

        private String code;

        DataStatEnum(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

    }

    /**
     * 商户平台角色管理
     * //000 客户会员 100 商户boss 200 商户财务 300 商户店长 400 商户收银员 500 商户服务员
     */
    public enum RoleNameEnum {

        VIP_ROLE_MCHANT("1", "000", "客户会员"),
        BOSS_ROLE_MCHANT("2", "100", "商户boss"),
        FINANCIAL_RORE_MCHANT("3", "200", "商户财务"),
        SHOPMANAGER_ROLE_MCHANT("4", "300", "商户店长"),
        CASHIER_ROLE_MCHANT("5", "400", "商户收银员"),
        WAITER_ROLE_MCHANT("6", "500", "商户服务员");

        private String roleId;
        private String roleType; //000 客户会员 100 商户boss 200 商户财务 300 商户店长 400 商户收银员 500 商户服务员
        private String roleName;

        RoleNameEnum(String roleId, String roleType, String roleName) {
            this.roleId = roleId;
            this.roleType = roleType;
            this.roleName = roleName;
        }

        public String getRoleId() {
            return roleId;
        }

        public String getRoleType() {
            return roleType;
        }

        public String getRoleName() {
            return roleName;
        }

        public static String findNameByCode(String roleType) {
            for (RoleNameEnum t : RoleNameEnum.values()) {
                if (t.roleType.equalsIgnoreCase(roleType)) {
                    return t.roleName;
                }
            }
            return null;
        }
    }

    /**
     * 交易类型
     */
    public enum TransCode {
        MB80("B80", "商户开户"),
        MB10("B10", "商户提现"),
        MB20("B20", "商户沉淀资金账户充值"),
        MB30("B30", "商户网站退货交易"),
        CW80("W80", "开户"),
        CW81("W81", "密码重置"),
        CW10("W10", "消费"),
        CW20("W20", "充值"),
        CW71("W71", "快捷消费"),
        CW11("W11", "退款"),
        CW74("W74", "退款（快捷）"),
        CW30("W30", "退货"),
        CW75("W75", "退货（快捷）"),
        CS20("S20", "返利");
        private String code;

        private String value;

        TransCode(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static TransCode findByCode(String code) {
            for (TransCode t : TransCode.values()) {
                if (t.getCode().equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }


    /**
     * 账户类型 0:已关闭 1:建立中 2:已链接
     */
    public enum ACC_TYPE {
        CUSTOMER("100"), MERCHANT("200");
        private final String value;

        ACC_TYPE(String value) {
            this.value = value;
        }

        ;

        @Override
        public String toString() {
            return this.value;
        }
    }

    ;

    /**
     * 核心接口返回码
     */
    public enum ITFRespCode {
        CODE00("00", "交易成功"),
        CODE01("01", "查发卡行"),
        CODE03("03", "无效商户"),
        CODE04("04", "受限商户"),
        CODE06("06", "无效合同"),
        CODE07("07", "终端已经下载过TMK"),
        CODE08("08", "终端未签到"),
        CODE09("09", "文件不存在"),
        CODE10("10", "文件打开错误"),
        CODE12("12", "无效交易"),
        CODE13("13", "无效金额"),
        CODE14("14", "无效卡号包括销毁状态"),
        CODE15("15", "卡未找到"),
        CODE16("16", "卡未激活"),
        CODE17("17", "与原交易卡号不符"),
        CODE20("20", "无效应答"),
        CODE21("21", "账户不匹配"),
        CODE22("22", "怀疑操作有误"),
        CODE23("23", "不可接受的手续费"),
        CODE25("25", "未找到原交易"),
        CODE26("26", "原交易不成功"),
        CODE31("31", "路由失败-机构不支持"),
        CODE34("34", "有作弊嫌疑"),
        CODE36("36", "卡已锁定"),
        CODE39("39", "交易日期有误"),
        CODE40("40", "请求的功能尚不支持"),
        CODE41("41", "卡已挂失"),
        CODE42("42", "无效账户商户合同下未关联账户"),
        CODE44("44", "卡被注销"),
        CODE45("45", "卡被冻结"),
        CODE51("51", "余额不足"),
        CODE54("54", "过期的卡"),
        CODE55("55", "密码错"),
        CODE56("56", "无此卡记录"),
        CODE57("57", "不允许持卡人进行的交易"),
        CODE58("58", "不允许终端进行的交易"),
        CODE61("61", "POS单笔交易金额超限"),
        CODE62("62", "POS当天累计交易金额超限"),
        CODE63("63", "余额不正确"),
        CODE64("64", "与原交易金额不符"),
        CODE65("65", "消费次数超限"),
        CODE66("66", "充值次数超限"),
        CODE67("67", "网上单笔交易金额超限"),
        CODE68("68", "网上当天累计交易金额超限"),
        CODE72("72", "无效IC卡信息"),
        CODE74("74", "cvv2不正确"),
        CODE75("75", "密码错误次数超限"),
        CODE77("77", "pos批次不一致重新签到"),
        CODE80("80", "购卡次数超限"),
        CODE90("90", "系统正在批处理日切中"),
        CODE91("91", "通信失败"),
        CODE92("92", "设施找不到或无法达到"),
        CODE94("94", "重复交易"),
        CODE95("95", "加密失败"),
        CODE96("96", "系统故障"),
        CODE97("97", "无效终端"),
        CODE99("99", "格式错误"),
        CODEA0("A0", "MAC错");

        private String code;

        private String value;

        ITFRespCode(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static ITFRespCode findByCode(String code) {
            for (ITFRespCode itf : ITFRespCode.values()) {
                if (itf.code.equalsIgnoreCase(code)) {
                    return itf;
                }
            }
            return null;
        }
    }


    //	/**
    //	 *
    //	 * 交易类型
    //	 *
    //	 */
    //	public enum TransCode {
    //		MB80("B80", "商户开户"),
    //		MB10("B10", "商户提现"),
    //		MB20("B20", "商户沉淀资金账户充值"),
    //		MB30("B30", "商户网站退货交易"),
    //		CW80("W80", "客户开户"),
    //		CW81("W81", "密码重置"),
    //		CW10("W10", "消费"),
    //		CW20("W20", "充值"),
    //		CW71("W71", "快捷消费"),
    //		CW11("W11", "退款"),
    //		CW74("W74", "退款（快捷）"),
    //		CS20("S20", "返利");
    //
    //		private String code;
    //
    //		private String value;
    //
    //		TransCode(String code, String value) {
    //			this.code = code;
    //			this.value = value;
    //		}
    //
    //		public String getCode() {
    //			return code;
    //		}
    //
    //		public String getValue() {
    //			return value;
    //		}
    //
    //		public static TransCode findByCode(String code) {
    //			for (TransCode t : TransCode.values()) {
    //				if (t.code.equalsIgnoreCase(code)) {
    //					return t;
    //				}
    //			}
    //			return null;
    //		}
    //	}

    /**
     * 渠道类型
     */
    public enum ChannelCode {
        CHANNEL0("10001001"),//管理平台
        //		CHANNEL1("40001001"),// 商户开户、客户开户、密码重置、消费 (从微信公众号发起) /** 公众号（开茂）使用 **/
        CHANNEL1("40001010"),// 商户开户、客户开户、密码重置、消费 (从微信公众号发起) /** 公众号（衡翮）使用 **/
        CHANNEL2("40002001"),// 充值、商户提现  (从微信公众号发起)
        CHANNEL3("40003001"),// 充值、商户提现  (从支付宝发起)
        CHANNEL4("40004001"),// 充值、商户提现  (从嘉福平台发起)
        CHANNEL5("40005001"),// 充值、商户提现  (从网银向本系统发起)
        CHANNEL6("40006001"),// all  (从电商端发起)
        CHANNEL7("40001002"),// 消费 (会员卡兼容通卡)
        CHANNEL8("40007001"),//手机充值(话费充值)
        CHANNEL9("40008001"),//卡券集市
        CHANNEL10("40007002");//手机充值（流量充值）

        private final String value;

        ChannelCode(String value) {
            this.value = value;
        }

        ;

        @Override
        public String toString() {
            return this.value;
        }

        public static ChannelCode findByCode(String code) {
            for (ChannelCode t : ChannelCode.values()) {
                if (t.value.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }

    /**
     * 发起方
     */
    public enum SponsorCode {
        SPONSOR00("00"),// 薪无忧客户微信平台
        SPONSOR10("10"),// 薪无忧商户微信平台
        SPONSOR20("20"),// 扫码盒子会员卡
        SPONSOR30("30"),// 扫码盒子微信
        SPONSOR40("40"),// 扫码盒子支付宝
        SPONSOR50("50");// 扫码盒子云闪付

        private final String value;

        SponsorCode(String value) {
            this.value = value;
        }

        ;

        @Override
        public String toString() {
            return this.value;
        }

        public static SponsorCode findByCode(String code) {
            for (SponsorCode t : SponsorCode.values()) {
                if (t.value.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }


    /**
     * 用户信息表  账户类型
     */
    public enum UserTypeEnum {
        APP_TYPE("00", "APP"),
        WX_TYPE("10", "微信"),
        WX_TYPE_JF("11", "嘉福微信"),
        WG_TYPE("20", "网关"),
        OMS_TYPE("90", "管理平台"),
        HN_TYPE("91", "卡密黄牛");

        private String code;
        private String name;

        UserTypeEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 图片管理表 应用种类
     */
    public enum Application {
        APP_MCHNT("10", "商户"),
        APP_SHOP("20", "门店"),
        APP_PROD("30", "产品");

        private String code;
        private String name;

        Application(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 图片管理表 应用类型
     */
    public enum AppType {
        A1001("1001", "商户LOGO"),
        A1002("1002", "组织机构文件代码文件"),
        A1003("1003", "企业工商营业执照文件"),
        A1004("1004", "法人身份证正面照片"),
        A2001("2001", "店铺照"),
        A2002("2002", "店内照"),
        A3001("3001", "产品卡面");

        private String code;
        private String name;

        AppType(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 行业类型
     */
    public enum IndustryType {
        IT1("1", "美食"),
        IT2("2", "休闲娱乐"),
        IT3("3", "生活服务"),
        IT4("4", "运输票务"),
        IT5("5", "电影票"),
        IT6("6", "旅游"),
        IT7("7", "酒店"),
        IT8("8", "购物"),
        IT9("9", "虚拟"),
        IT10("10", "网络传媒");

        private String code;
        private String value;

        IndustryType(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static IndustryType findByCode(String code) {
            for (IndustryType t : IndustryType.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }

    /**
     * 支付请求发起平台类型
     *
     * @author zqy
     */
    public enum PayReqTypeEnum {

        REQ_TYPE_A("A"),//API系统
        REQ_TYPE_B("B"),//商户平台
        REQ_TYPE_C("C");//wxp

        private String code;

        PayReqTypeEnum(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

    }

    /**
     * websocket 微信扫码支付 类型
     */
    public enum WSSendTypeEnum {

        SEND_TYPE_00("00", "保持心跳"),
        SEND_TYPE_10("10", "订单支付请求"),
        SEND_TYPE_20("20", "请求输入密码"),
        SEND_TYPE_80("80", "微信支付 后台处理通知"),
        SEND_TYPE_90("90", "扫描支付结果"),
        SEND_TYPE_99("99", "websocket异常");

        private String code;

        private String name;

        WSSendTypeEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 用户二维码 支付方式类型
     */
    public enum PayTypeEnum {
        VIPCARD_PAY("VIPCARD_PAY", "会员卡支付"),
        WECHAT_PAY("WECHAT_PAY", "微信支付");

        private String code;

        private String name;

        PayTypeEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * redis 订阅频道
     *
     * @author zqy
     */
    public enum RedisChannelEnum {
        B_SCAN_QR_CODE_PAY("B_SCAN_QR_CODE_PAY", "微信扫码支付订阅频道");

        private String code;

        private String name;

        RedisChannelEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 密码重置 账户类型
     *
     * @author zqy
     */
    public enum PWDAcctType {

        CUSTOMER_PWD_100("100", "会员密码"),
        MERCHANT_PWD_200("200", "商户管理员密码");

        private String code;
        private String name;

        PWDAcctType(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 渠道类型
     */
    public enum RechargeSpecialChannelCode {

        Channel50002001("50002001", "40002001", "微信钱包充值"),
        Channel50004001("50004001", "40004001", "嘉福平台充值");

        private String channelCode;
        private String orgChannelCode;
        private String value;

        @Override
        public String toString() {
            return this.channelCode;
        }

        public static RechargeSpecialChannelCode findChannelCodeByOrgCode(String code) {
            for (RechargeSpecialChannelCode t : RechargeSpecialChannelCode.values()) {
                if (t.orgChannelCode.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }

        RechargeSpecialChannelCode(String channelCode, String orgChannelCode, String value) {
            this.channelCode = channelCode;
            this.orgChannelCode = orgChannelCode;
            this.value = value;
        }

        public String getChannelCode() {
            return channelCode;
        }

        public String getOrgChannelCode() {
            return orgChannelCode;
        }

        public String getValue() {
            return value;
        }
    }

    public enum PaymentType {
        INVALID("INVALID"),
        HKBPAY("HKB"),
        WXPAY("WXPAY"),
        ALIPAY("ALIPAY"),
        UNIONPAY("UNIONPAY");

        private String value;

        PaymentType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 商户会员卡余额不足时，是否启用扣除福利余额
     *
     * @author kpplg
     */
    public enum MchntTypeEnum {

        MCHNTTYPE00("00", "启用"),
        MCHNTTYPE01("01", "禁用");

        private String code;
        private String name;

        MchntTypeEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public static String findMchntType(String code) {
            for (MchntTypeEnum t : MchntTypeEnum.values()) {
                if (t.getCode().contains(code)) {
                    return t.getName();
                }
            }
            return null;
        }
    }

    /**
     * 商户开户标识
     *
     * @author xiaomei
     */
    public enum mchntAccountStat {

        MAS00("00", "未开户"),
        MAS01("10", "已开户");

        private String code;
        private String name;

        mchntAccountStat(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public static String findmchntAccountStat(String code) {
            for (mchntAccountStat t : mchntAccountStat.values()) {
                if (t.getCode().contains(code)) {
                    return t.getName();
                }
            }
            return null;
        }
    }

    /**
     * 卡产品类型（卡劵集市）
     *
     * @author xiaomei
     */
    public enum CardProductType {
        CP11("11", "三网通话费卡"),
        CP12("12", "流量卡"),
        CP21("21", "爱奇艺月卡"),
        CP31("31", "加油卡"),
        CP41("41", "Q币");

        private String code;
        private String value;

        CardProductType(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static CardProductType findByCode(String code) {
            for (CardProductType t : CardProductType.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }

    /**
     * 订单类型（卡劵集市）
     *
     * @author xiaomei
     */
    public enum orderType {
        O1("10", "购买"),
        O2("20", "充值"),
        O3("30", "转让");

        private String code;
        private String value;

        orderType(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static orderType findByCode(String code) {
            for (orderType t : orderType.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }

    /**
     * 订单状态（卡劵集市）
     *
     * @author xiaomei
     */
    public enum orderStat {
        OS10("10", "购卡中"),
        OS11("11", "购卡失败"),
        OS12("12", "购卡成功"),
        OS20("20", "充值受理中"),
        OS21("21", "充值失败"),
        OS22("22", "充值成功"),
        OS23("23", "充值初始状态"),
        OS24("24", "充值完成失败"),
        OS30("30", "转让中"),
        OS31("31", "转让失败"),
        OS32("32", "转让成功"),
        OS33("33", "转让受理中"),
        OS34("34", "转让初始状态"),
        OS35("35", "转让完成失败");

        private String code;
        private String value;

        orderStat(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static orderStat findByCode(String code) {
            for (orderStat t : orderStat.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }

    /**
     * 交易类型（卡劵集市）
     *
     * @author xiaomei
     */
    public enum TransType {
        W10("W10", "购卡"),
        W20("W20", "充值"),
        W30("W30", "转让"),
        W40("W40", "余额提现");

        private String code;
        private String value;

        TransType(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static TransType findByCode(String code) {
            for (TransType t : TransType.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }

    /**
     * 手续费类型（卡劵集市）
     *
     * @author xiaomei
     */
    public enum TransFeeType {
        TF11("11", "三网通话费卡"),
        TF12("12", "流量卡"),
        TF21("21", "爱奇艺月卡"),
        TF31("31", "加油卡"),
        TF41("41", "Q币");

        private String code;
        private String value;

        TransFeeType(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static TransFeeType findByCode(String code) {
            for (TransFeeType t : TransFeeType.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }

    /**
     * 银行卡类型（卡劵集市）
     *
     * @author xiaomei
     */
    public enum bankType {
        B1("DC", "储蓄卡"),
        B2("CC", "信用卡");

        private String code;
        private String value;

        bankType(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static bankType findByCode(String code) {
            for (bankType t : bankType.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }

    /**
     * 银行卡信息标志
     *
     * @author xiaomei
     */
    public enum bankCheck {
        B0("0", "银行卡号不存在"),
        B1("1", "银行卡号已被绑定"),
        B2("2", "银行卡号不正确"),
        B3("3", "银行卡类型为信用卡");

        private String code;
        private String value;

        bankCheck(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static bankCheck findByCode(String code) {
            for (bankCheck t : bankCheck.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }

    /**
     * 移动流量（卡劵集市）
     *
     * @author xiaomei
     *
     *//*
	public enum YDFlowType {
		YD10M("14586", "10M", "3", "2.07"),
		YD20M("15039", "20M", "4", "2.76"),
		YD30M("14587", "30M", "5", "3.45"),
		YD50M("15040", "50M", "6", "4.14"),
		YD70M("14588", "70M", "10", "6.9"),
		YD100M("14597", "100M", "10", "6.9"),
		YD150M("14589", "150M", "20", "13.8"),
		YD200M("15041", "200M", "20", "13.8"),
		YD300M("14598", "300M", "20", "15"),
		YD500M("14590", "500M", "30", "18.9"),
		YD1G("16020", "1G", "50", "29.5");
		
		private String itemId;
		private String value;
		private String amount;
		private String chnlAmount;
		
		private YDFlowType(String itemId, String value, String amount, String chnlAmount) {
			this.itemId = itemId;
			this.value = value;
			this.amount = amount;
			this.chnlAmount = chnlAmount;
		}

		public String getItemId() {
			return itemId;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getChnlAmount() {
			return chnlAmount;
		}

		public void setChnlAmount(String chnlAmount) {
			this.chnlAmount = chnlAmount;
		}
		
		public static YDFlowType findByValue(String value) {
			for (YDFlowType t : YDFlowType.values()) {
				if (t.value.equalsIgnoreCase(value)) {
					return t;
				}
			}
			return null;
		}
		
		public static YDFlowType findByItemId(String itemId) {
			for (YDFlowType t : YDFlowType.values()) {
				if (t.itemId.equalsIgnoreCase(itemId)) {
					return t;
				}
			}
			return null;
		}
	}
	
	*//**
     * 联通流量（卡劵集市）
     *
     * @author xiaomei
     *
     *//*
	public enum LTFlowType {
		LT100M("16427", "100M", "10", "12.3"),
		LT200M("16428", "200M", "15", "8.2");
		
		private String itemId;
		private String value;
		private String amount;
		private String chnlAmount;
		
		private LTFlowType(String itemId, String value, String amount, String chnlAmount) {
			this.itemId = itemId;
			this.value = value;
			this.amount = amount;
			this.chnlAmount = chnlAmount;
		}

		public String getItemId() {
			return itemId;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getChnlAmount() {
			return chnlAmount;
		}

		public void setChnlAmount(String chnlAmount) {
			this.chnlAmount = chnlAmount;
		}
		
		public static LTFlowType findByValue(String value) {
			for (LTFlowType t : LTFlowType.values()) {
				if (t.value.equalsIgnoreCase(value)) {
					return t;
				}
			}
			return null;
		}
		
		public static LTFlowType findByItemId(String itemId) {
			for (LTFlowType t : LTFlowType.values()) {
				if (t.itemId.equalsIgnoreCase(itemId)) {
					return t;
				}
			}
			return null;
		}
	}
	
	*//**
     * 电信流量（卡劵集市）
     *
     * @author xiaomei
     *
     *//*
	public enum DXFlowType {
		DX100M("14642", "100M", "10", "20.4"),
		DX200M("14643", "200M", "15", "10.2"),
		DX500M("14644", "500M", "30", "6.8");
		
		private String itemId;
		private String value;
		private String amount;
		private String chnlAmount;
		
		private DXFlowType(String itemId, String value, String amount, String chnlAmount) {
			this.itemId = itemId;
			this.value = value;
			this.amount = amount;
			this.chnlAmount = chnlAmount;
		}

		public String getItemId() {
			return itemId;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getChnlAmount() {
			return chnlAmount;
		}

		public void setChnlAmount(String chnlAmount) {
			this.chnlAmount = chnlAmount;
		}
		
		public static DXFlowType findByValue(String value) {
			for (DXFlowType t : DXFlowType.values()) {
				if (t.value.equalsIgnoreCase(value)) {
					return t;
				}
			}
			return null;
		}
		
		public static DXFlowType findByItemId(String itemId) {
			for (DXFlowType t : DXFlowType.values()) {
				if (t.itemId.equalsIgnoreCase(itemId)) {
					return t;
				}
			}
			return null;
		}
	}
	
	public enum PrepaidRechargeType{
//		PrepaidRecharge001("pr001","1元","1","1.045"),
//		PrepaidRecharge010("pr010","10元","10","10.45"),
//		PrepaidRecharge020("pr020","20元","20","20.9"),
//		PrepaidRecharge030("pr030","30元","30","31.35"),
		PrepaidRecharge050("pr50","50元","50","52.25"),
		PrepaidRecharge100("pr100","100元","100","104.5"),
		PrepaidRecharge200("pr200","200元","200","209"),
		PrepaidRecharge300("pr300","300元","300","313.5"),
		PrepaidRecharge500("pr500","500元","500","522.5");
		
		private String itemId;
		private String rechargeAmt;
		private String transAmt;
		private String channelAmt;
		
		private PrepaidRechargeType(String itemId, String rechargeAmt, String transAmt, String channelAmt) {
			this.itemId = itemId;
			this.rechargeAmt = rechargeAmt;
			this.transAmt = transAmt;
			this.channelAmt = channelAmt;
		}

		public String getItemId() {
			return itemId;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		public String getRechargeAmt() {
			return rechargeAmt;
		}

		public void setRechargeAmt(String rechargeAmt) {
			this.rechargeAmt = rechargeAmt;
		}

		public String getTransAmt() {
			return transAmt;
		}

		public void setTransAmt(String transAmt) {
			this.transAmt = transAmt;
		}

		public String getChannelAmt() {
			return channelAmt;
		}

		public void setChannelAmt(String channelAmt) {
			this.channelAmt = channelAmt;
		}
		
		public static PrepaidRechargeType findByItemId(String itemId) {
			for (PrepaidRechargeType t : PrepaidRechargeType.values()) {
				if (t.itemId.equalsIgnoreCase(itemId)) {
					return t;
				}
			}
			return null;
		}
		
		public static PrepaidRechargeType findByRechargeAmt(String rechargeAmt) {
			for (PrepaidRechargeType t : PrepaidRechargeType.values()) {
				if (t.rechargeAmt.equalsIgnoreCase(rechargeAmt)) {
					return t;
				}
			}
			return null;
		}
	}*/

    /**
     * 手机充值订单类型
     *
     * @author xiaomei
     */
    public enum phoneRechargeOrderType {
        PROT1("1", "话费充值"),
        PROT2("2", "流量充值");

        private String code;
        private String value;

        phoneRechargeOrderType(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static phoneRechargeOrderType findByCode(String code) {
            for (phoneRechargeOrderType t : phoneRechargeOrderType.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }

    /**
     * 话费充值渠道类型
     *
     * @author xiaomei
     */
    public enum phoneRechargeReqChnl {
        PRRC1("P1001", "福利余额"),
        PRRC2("P1002", "卡密充值"),
        PRRC3("P1003", "API接口充值");

        private String code;
        private String value;

        phoneRechargeReqChnl(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static phoneRechargeReqChnl findByCode(String code) {
            for (phoneRechargeReqChnl t : phoneRechargeReqChnl.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }

    /**
     * 话费充值供应商
     *
     * @author xiaomei
     */
    public enum phoneRechargeSupplier {
        PRS1("S1001", "力方");
//		PRS2("S1002", " 鼎驰");

        private String code;
        private String value;

        phoneRechargeSupplier(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static phoneRechargeSupplier findByCode(String code) {
            for (phoneRechargeSupplier t : phoneRechargeSupplier.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }

    /**
     * 手机充值交易状态
     *
     * @author xiaomei
     */
    public enum phoneRechargeTransStat {
        PRTS0("0", "未付款"),
        PRTS1("1", "充值中"),
        PRTS2("2", "充值成功"),
        PRTS3("3", "充值失败"),
        PRTS4("4", "受理成功"),
        PRTS5("5", "退款成功"),
        PRTS6("6", "退款失败");

        private String code;
        private String value;

        phoneRechargeTransStat(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static phoneRechargeTransStat findByCode(String code) {
            for (phoneRechargeTransStat t : phoneRechargeTransStat.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }

    public enum OperatorType {
        OperatorType1("1", "移动"),
        OperatorType2("2", "联通"),
        OperatorType3("3", "电信");

        private String code;
        private String value;

        OperatorType(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static String findByCode(String code) {
            for (OperatorType t : OperatorType.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t.getValue();
                }
            }
            return null;
        }
    }

    public enum IsUsableType {
        IsUsableType1("0", "是"),
        IsUsableType2("1", "否");

        private String code;
        private String value;

        IsUsableType(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static IsUsableType findByCode(String code) {
            for (IsUsableType t : IsUsableType.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }

    public enum ShopType {
        ShopType1("1", "话费");
//		ShopType2("2","流量");

        private String code;
        private String value;

        ShopType(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static String findByCode(String code) {
            for (ShopType t : ShopType.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t.getValue();
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        for (OperatorType t : OperatorType.values()) {
            if (t.code.equalsIgnoreCase("1")) {
                System.out.println(t.getValue());
            }
        }
    }

    /**
     * 退款 模板消息文案
     *
     * @author xiaomei
     */
    public enum templateMsgRefund {
        templateMsgRefund0("10001001", "退款成功"),
        templateMsgRefund1("40001010", "退款成功"),
        templateMsgRefund2("40002001", "退款成功"),
        templateMsgRefund3("40003001", "退款成功"),
        templateMsgRefund4("40004001", "退款成功"),
        templateMsgRefund5("40005001", "退款成功"),
        templateMsgRefund6("40006001", "商城购物异常"),
        templateMsgRefund7("40001002", "退款成功"),
        templateMsgRefund8("40007001", "手机充值异常"),
        TemplateMsgRefund9("40008001", "卡券购买异常"),
        templateMsgRefund10("40007002", "手机充值异常");

        private String code;
        private String value;

        templateMsgRefund(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static templateMsgRefund findByCode(String code) {
            for (templateMsgRefund t : templateMsgRefund.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }

    public enum ShopUnitType {
        ShopUnitType01("1", "1", "元");

        private String code;
        private String typeCode;
        private String value;

        ShopUnitType(String code, String typeCode, String value) {
            this.code = code;
            this.typeCode = typeCode;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getTypeCode() {
            return typeCode;
        }

        public String getValue() {
            return value;
        }

        public static String findByCode(String code) {
            for (ShopUnitType t : ShopUnitType.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t.getValue();
                }
            }
            return null;
        }

    }

    /**
     * 供应商信息-默认路由
     *
     * @author kpplg
     */
    public enum providerDefaultRoute {
        DefaultRoute0("0", "是"),
        DefaultRoute1("1", "否");

        private String code;
        private String value;

        providerDefaultRoute(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static String findByCode(String code) {
            for (providerDefaultRoute t : providerDefaultRoute.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t.getValue();
                }
            }
            return null;
        }
    }

    /**
     * 供应商订单-充值状态
     *
     * @author kpplg
     */
    public enum providerOrderRechargeState {
        RechargeState0("0", "充值中"),
        RechargeState1("1", "充值成功"),
        RechargeState3("3", "充值失败"),
        RechargeState8("8", "待充值"),
        RechargeState9("9", "撤销");

        private String code;
        private String value;

        providerOrderRechargeState(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static String findByCode(String code) {
            for (providerOrderRechargeState t : providerOrderRechargeState.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t.getValue();
                }
            }
            return null;
        }
    }

    /**
     * 供应商订单-支付状态
     *
     * @author kpplg
     */
    public enum providerOrderPayState {
        PayState1("1", "已扣款"),
        PayState2("2", "已退款");

        private String code;
        private String value;

        providerOrderPayState(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static String findByCode(String code) {
            for (providerOrderPayState t : providerOrderPayState.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t.getValue();
                }
            }
            return null;
        }
    }

    /**
     * 分销商订单-订单状态
     *
     * @author kpplg
     */
    public enum ChannelOrderStat {
        ChannelOrderStat0("0", "待扣款"),
        ChannelOrderStat1("1", "已扣款"),
        ChannelOrderStat2("2", "已退款"),
        ChannelOrderStat5("5", "退款中");

        private String code;
        private String value;

        ChannelOrderStat(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static String findByCode(String code) {
            for (ChannelOrderStat t : ChannelOrderStat.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t.getValue();
                }
            }
            return null;
        }
    }

    /**
     * 分销商订单-通知状态
     *
     * @author kpplg
     */
    public enum ChannelOrderNotifyStat {
        ChannelOrderNotifyStat1("1", "处理中"),
        ChannelOrderNotifyStat2("2", "处理失败"),
        ChannelOrderNotifyStat3("3", "处理成功");

        private String code;
        private String value;

        ChannelOrderNotifyStat(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static String findByCode(String code) {
            for (ChannelOrderNotifyStat t : ChannelOrderNotifyStat.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t.getValue();
                }
            }
            return null;
        }
    }

    /**
     * 分销商充值产品-是否区分地区
     *
     * @author kpplg
     */
    public enum ChannelProductAreaFlag {
        ChannelProductAreaFlag0("0", "区分"),
        ChannelProductAreaFlag1("1", "不区分");

        private String code;
        private String value;

        ChannelProductAreaFlag(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static String findByCode(String code) {
            for (ChannelProductAreaFlag t : ChannelProductAreaFlag.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t.getValue();
                }
            }
            return null;
        }
    }

    /**
     * 分销商充值产品-产品类型
     *
     * @author kpplg
     */
    public enum ChannelProductProType {
        ChannelProductProType1("1", "话费"),
        ChannelProductProType2("2", "流量"),
        ChannelProductProType3("3", "其他");

        private String code;
        private String value;

        ChannelProductProType(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static String findByCode(String code) {
            for (ChannelProductProType t : ChannelProductProType.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t.getValue();
                }
            }
            return null;
        }
    }

    public enum ChannelReserveType {
        ChannelReserveType1("1", "追加"),
        ChannelReserveType2("2", "撤销");

        private String code;
        private String value;

        ChannelReserveType(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static String findByCode(String code) {
            for (ChannelReserveType t : ChannelReserveType.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t.getValue();
                }
            }
            return null;
        }
    }

    /**
     * 模板消息 显示支付渠道信息
     *
     * @author xiaomei
     */
    public enum templateMsgPayment {
        templateMsgPayment1("40006001", "海易通商城"),
        templateMsgPayment2("40006002", "家乐宝商城"),
        templateMsgPayment3("40006003", "京东商城"),
        templateMsgPayment4("40006004", "美团"),
        templateMsgPayment5("40006005", "大众点评"),
        templateMsgPayment6("40006006", "自营商城"),
        templateMsgPayment7("40006007", "海豚通商城"),
        templateMsgPayment8("40007001", "手机充值"),
        templateMsgPayment9("40008001", "卡券集市");

        private String code;
        private String value;

        templateMsgPayment(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static templateMsgPayment findByCode(String code) {
            for (templateMsgPayment t : templateMsgPayment.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }

    /**
     * 退款接口 标志
     *
     * @author xiaomei
     */
    public enum refundFalg {
        refundFalg1("1", "系统退款"),
        refundFalg2("2", "用户端退款");

        private String code;
        private String value;

        refundFalg(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static refundFalg findByCode(String code) {
            for (refundFalg t : refundFalg.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }


    /**
     * 供应商CODE 标志
     *
     * @author zhuqiuyou
     */
    public enum TeleProviderEnum {
        BM001("BM001", "立方"),
        BM002("BM002", "测试商户-可替换");

        private String code;
        private String value;

        TeleProviderEnum(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static refundFalg findByCode(String code) {
            for (refundFalg t : refundFalg.values()) {
                if (t.code.equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return null;
        }
    }
	
	/*public enum MSMTypeEnum{
		MSMTypeEnum1("1", "短信通知"), 
		MSMTypeEnum2("2", "验证码");

		private String code;
		private String value;

		MSMTypeEnum(String code, String value) {
			this.code = code;
			this.value = value;
		}

		public String getCode() {
			return code;
		}

		public String getValue() {
			return value;
		}

		public static MSMTypeEnum findByCode(String code) {
			for (MSMTypeEnum t : MSMTypeEnum.values()) {
				if (t.code.equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
	}*/

}
