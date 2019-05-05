package com.cn.thinkx.pms.base.redis.util;

import com.cn.thinkx.pms.base.utils.MD5Util;
import com.cn.thinkx.pms.base.utils.StringUtil;

public class SignatureUtil {

    /**
     * 生成商户扫用户交易报文签名
     *
     * @param settleDate
     * @param swtFlowNo
     * @param issChannel
     * @param innerMerchantNo
     * @param innerShopNo
     * @param txnAmount
     * @param oriTxnAmount
     * @param cardNo
     * @param pinTxn
     * @param timestamp
     * @return
     */
    public static String genB2CTransMsgSignature(String settleDate, String swtFlowNo, String issChannel,
                                                 String innerMerchantNo, String innerShopNo, String txnAmount, String oriTxnAmount, String cardNo,
                                                 String pinTxn, long timestamp) {
        return MD5Util.md5(settleDate + swtFlowNo + issChannel + innerMerchantNo + innerShopNo + txnAmount
                + oriTxnAmount + cardNo + pinTxn + timestamp + BaseKeyUtil.getEncodingAesKey());
    }

    /***
     * 报文签名
     * @param settleDate
     * @param swtFlowNo
     * @param issChannel
     * @param cardNo
     * @param accType
     * @param pinTxn
     * @param timestamp
     * @return
     */
    public static String getTxnBeanSignature(String settleDate, String swtFlowNo, String issChannel, String cardNo, String TxnAmount, String pinTxn, long timestamp) {
        return MD5Util.md5(settleDate +
                swtFlowNo +
                issChannel +
                StringUtil.nullToString(cardNo) +
                StringUtil.nullToString(TxnAmount) +
                StringUtil.nullToString(pinTxn) +
                timestamp);
    }
}
