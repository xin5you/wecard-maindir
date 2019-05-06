package com.cn.thinkx.dubbo.facadeImpl.txn.valid;

import com.cn.thinkx.beans.TxnPackageBean;
import com.cn.thinkx.dubbo.entity.TxnResp;
import com.cn.thinkx.pms.base.redis.util.TxnChannelSignatureUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;

public class Java2TxnBusinessValidUtil {

    public static boolean consumeTransactionITFVaild(TxnPackageBean txn, TxnResp resp) {
        if (StringUtil.isNullOrEmpty(txn.getChannel())) {
            resp.setInfo("渠道号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(txn.getIssChannel())) {
            resp.setInfo("机构号为空");
            return true;
        }
//		if (StringUtil.isNullOrEmpty(txn.getUserId())) {
//			resp.setInfo("用户为空");
//			return true;
//		}
        if (StringUtil.isNullOrEmpty(txn.getInnerMerchantNo())) {
            resp.setInfo("商户号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(txn.getCardNo())) {
            resp.setInfo("卡号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(txn.getTxnAmount())) {
            resp.setInfo("交易金额为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(txn.getOriTxnAmount())) {
            resp.setInfo("原交易金额为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(txn.getTimestamp())) {
            resp.setInfo("时间戳为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(txn.getSignature())) {
            resp.setInfo("签名为空");
            return true;
        }
        if (!TxnChannelSignatureUtil.genSign(txn).equals(txn.getSignature())) {
            resp.setInfo("签名错误");
            return true;
        }
        if (TxnChannelSignatureUtil.isSignExpired(System.currentTimeMillis(), txn.getTimestamp())) {
            resp.setInfo("签名过期");
            return true;
        }
        return false;
    }

    public static boolean quickConsumeTransactionITFVaild(TxnPackageBean txn, TxnResp resp) {
        if (StringUtil.isNullOrEmpty(txn.getChannel())) {
            resp.setInfo("渠道号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(txn.getIssChannel())) {
            resp.setInfo("机构号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(txn.getInnerMerchantNo())) {
            resp.setInfo("商户号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(txn.getCardNo())) {
            resp.setInfo("卡号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(txn.getTxnAmount())) {
            resp.setInfo("交易金额为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(txn.getOriTxnAmount())) {
            resp.setInfo("原交易金额为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(txn.getTimestamp())) {
            resp.setInfo("时间戳为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(txn.getSignature())) {
            resp.setInfo("签名为空");
            return true;
        }
        if (!TxnChannelSignatureUtil.genSign(txn).equals(txn.getSignature())) {
            resp.setInfo("签名错误");
            return true;
        }
        if (TxnChannelSignatureUtil.isSignExpired(System.currentTimeMillis(), txn.getTimestamp())) {
            resp.setInfo("签名过期");
            return true;
        }
        return false;
    }
}
