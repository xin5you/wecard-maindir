package com.cn.thinkx.itf.base;

import com.cn.thinkx.beans.TxnPackageBean;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.redis.util.SignatureUtil;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.ReadPropertiesFile;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pms.connect.entity.BizMessageObj;
import com.cn.thinkx.pms.connect.utils.ConnectConstant;

import java.util.UUID;

public class MessageUtils {

    /**
     * 初始化组装报文
     *
     * @return
     */
    public static BizMessageObj initMessageObj() {
        BizMessageObj msgObj = new BizMessageObj();
        msgObj.setMsgHead(ReadPropertiesFile.getInstance().getProperty(BaseConstants.MSG_HEAD, null));
        msgObj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
        msgObj.setServiceName(ConnectConstant.VTXN);
        return msgObj;
    }

    /**
     * 校验初始化报文信息
     *
     * @param txn
     */
    public static boolean isRequiredFiled(BizMessageObj obj) {
        if (StringUtil.isNullOrEmpty(obj.getTxnType())) {
            obj.setRespCode("交易类型为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(obj.getSwtTxnDate())) {
            obj.setRespCode("交易日期为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(obj.getSwtTxnTime())) {
            obj.setRespCode("交易时间为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(obj.getSwtSettleDate())) {
            obj.setRespCode("清算日期为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(obj.getChannel())) {
            obj.setRespCode("渠道号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(obj.getSwtFlowNo())) {
            obj.setRespCode("流水号为空");
            return true;
        }
        return false;
    }

    /**
     * 验证交易签名
     *
     * @param txnBean
     * @return
     */
    public static boolean validateSignature(TxnPackageBean txnBean) {
        String signature = SignatureUtil.genB2CTransMsgSignature(txnBean.getSwtSettleDate(), txnBean.getSwtFlowNo(),
                txnBean.getIssChannel(), txnBean.getInnerMerchantNo(), txnBean.getInnerShopNo(), txnBean.getTxnAmount(),
                txnBean.getOriTxnAmount(), txnBean.getCardNo(), txnBean.getPinTxn(), txnBean.getTimestamp());

        long currentTime = System.currentTimeMillis();// 系统当前时间
        int signatureTimeout = Integer.parseInt(RedisDictProperties.getInstance().getdictValueByCode("SIGNATURE_TIMEOUT"));
        long txnTimestamp = txnBean.getTimestamp();// 签名时间戳
        long valid = signatureTimeout * 60 * 1000;// 签名有效期(分钟转毫秒)

        if ((currentTime - txnTimestamp) > valid) {// 签名失效
            return false;
        }
        if (signature.equals(txnBean.getMac())) {// 签名验证通过
            return true;
        }
        return false;
    }

    /**
     * 验证交易撤销签名
     *
     * @param txnBean
     * @return
     */
    public static boolean validRefundSign(TxnPackageBean txnBean) {
        String signature = SignatureUtil.genB2CTransMsgSignature(txnBean.getSwtSettleDate(), txnBean.getSwtFlowNo(),
                txnBean.getIssChannel(), txnBean.getInnerMerchantNo(), txnBean.getTxnAmount(), txnBean.getOriSwtFlowNo(),
                txnBean.getOriTxnAmount(), txnBean.getCardNo(), txnBean.getPinTxn(), txnBean.getTimestamp());

        long currentTime = System.currentTimeMillis();// 系统当前时间
        int signatureTimeout = Integer.parseInt(RedisDictProperties.getInstance().getdictValueByCode("SIGNATURE_TIMEOUT"));
        long txnTimestamp = txnBean.getTimestamp();// 签名时间戳
        long valid = signatureTimeout * 60 * 1000;// 签名有效期(分钟转毫秒)

        if ((currentTime - txnTimestamp) > valid) {// 签名失效
            return false;
        }
        if (signature.equals(txnBean.getMac())) {// 签名验证通过
            return true;
        }
        return false;
    }


    /**
     * 设置签名
     *
     * @param txnBean
     * @return
     */
    public static boolean validateTxnBeanSignature(TxnPackageBean txnBean) {
        String signature = SignatureUtil.getTxnBeanSignature(txnBean.getSwtSettleDate(), txnBean.getSwtFlowNo(),
                txnBean.getIssChannel(), txnBean.getCardNo(), txnBean.getTxnAmount(), txnBean.getPinTxn(), txnBean.getTimestamp());

        long currentTime = System.currentTimeMillis();// 系统当前时间
        int signatureTimeout = Integer.parseInt(RedisDictProperties.getInstance().getdictValueByCode("SIGNATURE_TIMEOUT"));
        long txnTimestamp = txnBean.getTimestamp();// 签名时间戳
        long valid = signatureTimeout * 60 * 1000;// 签名有效期(分钟转毫秒)

        if ((currentTime - txnTimestamp) > valid) {// 签名失效
            return false;
        }
        if (signature.equals(txnBean.getSignature())) {// 签名验证通过
            return true;
        }
        return false;
    }

    /**
     * 业务bean值转换
     *
     * @param source
     * @param target
     */
    public static void txnPackageBean2BizMessageObj(TxnPackageBean source, BizMessageObj target) {
        target.setDataHead(source.getDataHead());
        target.setTxnType(source.getTxnType());
        target.setSwtTxnDate(source.getSwtTxnDate());
        target.setSwtTxnTime(source.getSwtTxnTime());
        target.setSwtSettleDate(source.getSwtSettleDate());
        target.setChannel(source.getChannel());
        target.setSwtBatchNo(source.getSwtBatchNo());
        target.setSwtFlowNo(source.getSwtFlowNo());
        target.setRecTxnDate(source.getRecTxnDate());
        target.setRecTxnTime(source.getRecTxnTime());
        target.setRecBatchNo(source.getRecBatchNo());
        target.setRecFlowNo(source.getRecFlowNo());
        target.setIssChannel(source.getIssChannel());
        target.setInnerMerchantNo(source.getInnerMerchantNo());
        target.setInnerShopNo(source.getInnerShopNo());
        target.setInnerPosNo(source.getInnerPosNo());
        target.setTrack2(source.getTrack2());
        target.setTrack3(source.getTrack3());
        target.setCardNo(source.getCardNo());
        target.setCvv2(source.getCvv2());
        target.setExpDate(source.getExpDate());
        target.setAccType(source.getAccType());
        target.setTxnAmount(source.getTxnAmount());
        target.setCardHolderFee(source.getCardHolderFee());
        target.setBalance(source.getBalance());
        target.setPinQuiry(source.getPinQuiry());
        target.setPinQuiryNew(source.getPinQuiryNew());
        target.setPinTxn(source.getPinTxn());
        target.setPinTxnNew(source.getPinTxnNew());
        target.setAuthCode(source.getAuthCode());
        target.setReferenceNo(source.getReferenceNo());
        target.setOriSwtBatchNo(source.getOriSwtBatchNo());
        target.setOriSwtFlowNo(source.getOriSwtFlowNo());
        target.setOriRecBatchNo(source.getOriRecBatchNo());
        target.setOriRecFlowNo(source.getOriRecFlowNo());
        target.setOriTxnAmount(source.getOriTxnAmount());
        target.setOriCardHolderFee(source.getOriCardHolderFee());
        target.setFilePath(source.getFilepath());
        target.setResv1(source.getResv1());
        target.setResv2(source.getResv2());
        target.setResv3(source.getResv3());
        target.setResv4(source.getResv4());
        target.setResv5(source.getResv5());
        target.setResv6(source.getResv6());
        target.setResv7(source.getResv7());
        target.setMac(source.getMac());
        target.setOtherdata(source.getOtherData());
    }

}
