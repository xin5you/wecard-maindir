package com.cn.thinkx.dubbo.facadeImpl.txn.utils;

import com.cn.thinkx.beans.TxnPackageBean;
import com.cn.thinkx.facade.bean.CusCardOpeningRequest;
import com.cn.thinkx.facade.bean.RechargeTransRequest;
import com.cn.thinkx.facade.bean.base.BaseTxnReq;
import com.cn.thinkx.itf.base.SignUtil;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransCode;
import com.cn.thinkx.pms.base.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class HKBTxnUtil {

    static Logger logger = LoggerFactory.getLogger(HKBTxnUtil.class);

    /**
     * 客户开卡报文转换
     *
     * @param req 外部请求参数对象
     * @param id  中间层流水号
     * @return
     */
    public static TxnPackageBean customerCardOpening(CusCardOpeningRequest req, String id) {
        TxnPackageBean txnBean = new TxnPackageBean();
        txnBean.setTxnType(TransCode.CW80.getCode() + "0");// 交易类型，发送报文时补0
        if (StringUtil.isNullOrEmpty(req.getSwtSettleDate())) {
            req.setSwtSettleDate(req.getSwtTxnDate());
        }

        txnBean.setSwtTxnDate(req.getSwtTxnDate());
        txnBean.setSwtTxnTime(req.getSwtTxnTime());
        txnBean.setSwtSettleDate(req.getSwtSettleDate());
        txnBean.setChannel(req.getChannel());
        txnBean.setSwtFlowNo(id);
        txnBean.setInnerMerchantNo(req.getInnerMerchantNo());
        /** 中间层在校验签名之后传入以下两个字段 */
        txnBean.setCardNo(req.getCardNo());
        txnBean.setIssChannel(req.getIssChannel());
        txnBean.setResv3(req.getResv3());// userId
        txnBean.setResv4(req.getResv4());// personId

        return txnBean;
    }

    /**
     * 充值交易报文转换
     *
     * @param req 外部请求参数对象
     * @param id  中间层流水号
     * @return
     */
    public static TxnPackageBean rechargeTrans(RechargeTransRequest req, String id) {
        TxnPackageBean txnBean = new TxnPackageBean();
        txnBean.setTxnType(TransCode.CW20.getCode() + "0");// 交易类型，发送报文时补0
        if (StringUtil.isNullOrEmpty(req.getSwtSettleDate())) {
            req.setSwtSettleDate(req.getSwtTxnDate());
        }
        req.setCardNo("U" + req.getCardNo());// 卡号 U开头为客户端交易，C开头则为刷卡交易

        txnBean.setSwtTxnDate(req.getSwtTxnDate());
        txnBean.setSwtTxnTime(req.getSwtTxnTime());
        txnBean.setSwtSettleDate(req.getSwtSettleDate());
        txnBean.setChannel(req.getChannel());
        txnBean.setSwtFlowNo(id);
        txnBean.setInnerMerchantNo(req.getInnerMerchantNo());
        txnBean.setCardNo(req.getCardNo());
        txnBean.setTxnAmount(req.getTxnAmount());
        txnBean.setOriTxnAmount(req.getOriTxnAmount());
        txnBean.setIssChannel(req.getIssChannel());
        txnBean.setInnerShopNo(req.getInnerShopNo());

        return txnBean;
    }


    /**
     * 充值送交易报文转换
     *
     * @param req        外部请求参数对象
     * @param id         中间层流水号
     * @param channel    渠道
     * @param transAmont 交易金额
     * @return
     */
    public static TxnPackageBean rechargeSpecialTrans(RechargeTransRequest req, String interfacePrimaryKey, String orglogId, String channel, String transAmont) {
        TxnPackageBean txnBean = new TxnPackageBean();
        txnBean.setTxnType(TransCode.CS20.getCode() + "0");// 交易类型，发送报文时补0
        if (StringUtil.isNullOrEmpty(req.getSwtSettleDate())) {
            req.setSwtSettleDate(req.getSwtTxnDate());
        }
        req.setCardNo(req.getCardNo());// 卡号 U开头为客户端交易，C开头则为刷卡交易
        txnBean.setSwtTxnDate(req.getSwtTxnDate());
        txnBean.setSwtTxnTime(req.getSwtTxnTime());
        txnBean.setSwtSettleDate(req.getSwtSettleDate());
        txnBean.setChannel(channel);
        txnBean.setSwtFlowNo(interfacePrimaryKey);
        txnBean.setOriSwtFlowNo(orglogId);
        txnBean.setInnerMerchantNo(req.getInnerMerchantNo());
        txnBean.setCardNo(req.getCardNo());
        txnBean.setTxnAmount(transAmont);
        txnBean.setOriTxnAmount(transAmont);
        txnBean.setIssChannel(req.getIssChannel());
        txnBean.setInnerShopNo(req.getInnerShopNo());
        return txnBean;
    }

    /**
     * 会员卡消费交易报文转换
     *
     * @param req 外部请求参数对象
     * @param id  中间层流水号
     * @return
     */
    public static TxnPackageBean hkbConsumeTrans(BaseTxnReq req, String id) {
        TxnPackageBean txnBean = new TxnPackageBean();
        txnBean.setTxnType(TransCode.CW10.getCode() + "0");// 交易类型，发送报文时补0
        if (StringUtil.isNullOrEmpty(req.getSwtSettleDate())) {
            req.setSwtSettleDate(req.getSwtTxnDate());
        }
        req.setCardNo("U" + req.getCardNo());// 卡号 U开头为客户端交易，C开头则为刷卡交易

        txnBean.setSwtTxnDate(req.getSwtTxnDate());
        txnBean.setSwtTxnTime(req.getSwtTxnTime());
        txnBean.setSwtSettleDate(req.getSwtSettleDate());
        txnBean.setChannel(req.getChannel());
        txnBean.setSwtFlowNo(id);
        txnBean.setInnerMerchantNo(req.getInnerMerchantNo());
        txnBean.setCardNo(req.getCardNo());
        txnBean.setTxnAmount(req.getTxnAmount());
        txnBean.setOriTxnAmount(req.getOriTxnAmount());
        txnBean.setIssChannel(req.getIssChannel());
        txnBean.setInnerShopNo(req.getInnerShopNo());

        return txnBean;
    }

    /**
     * 快捷支付交易报文转换
     *
     * @param req 外部请求参数对象
     * @param id  中间层流水号
     * @return
     */
    public static TxnPackageBean quickPaymentTrans(BaseTxnReq req, String id) {
        TxnPackageBean txnBean = new TxnPackageBean();
        txnBean.setTxnType(TransCode.CW71.getCode() + "0");// 交易类型，发送报文时补0
        if (StringUtil.isNullOrEmpty(req.getSwtSettleDate())) {
            req.setSwtSettleDate(req.getSwtTxnDate());
        }
        req.setCardNo("U" + req.getCardNo());// 卡号 U开头为客户端交易，C开头则为刷卡交易

        txnBean.setSwtTxnDate(req.getSwtTxnDate());
        txnBean.setSwtTxnTime(req.getSwtTxnTime());
        txnBean.setSwtSettleDate(req.getSwtSettleDate());
        txnBean.setChannel(req.getChannel());
        txnBean.setSwtFlowNo(id);
        txnBean.setInnerMerchantNo(req.getInnerMerchantNo());
        txnBean.setCardNo(req.getCardNo());
        txnBean.setTxnAmount(req.getTxnAmount());
        txnBean.setOriTxnAmount(req.getOriTxnAmount());
        txnBean.setIssChannel(req.getIssChannel());
        txnBean.setInnerShopNo(req.getInnerShopNo());

        return txnBean;
    }

    /**
     * 转换交易报文返回的余额
     *
     * @param msgAmount 报文返回金额
     * @param begin     截取开始位
     * @param end       截取结束位
     * @return
     */
    public static String transMsgAmount(String msgAmount, int begin, int end) {
        String amount = msgAmount.substring(begin, end);
        amount = "" + Integer.parseInt(amount);
        return amount;
    }

    public static String hkbPayBackToJF(Map<String, String> paramMap) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            String url = RedisDictProperties.getInstance().getdictValueByCode("PAY_BACK_URL");
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Charset", "UTF-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());

            // 设置请求属性
            String param = "";
            if (paramMap != null && paramMap.size() > 0) {
                Iterator<String> ite = paramMap.keySet().iterator();
                while (ite.hasNext()) {
                    String key = ite.next();// key
                    String value = paramMap.get(key);
                    param += key + "=" + value + "&";
                }
                param = param.substring(0, param.length() - 1);
            }

            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("发送退款POST请求出现异常", e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                logger.error("发送退款POST请求出现异常", ex);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        SortedMap<String, String> map = new TreeMap<String, String>();
        map.put("orderId", "937460105621");
        map.put("extOrderId", "2017010507102600001175");
        map.put("userId", "53627");
        map.put("timestamp", "" + System.currentTimeMillis());
        map.put("sign", SignUtil.sign(map, "PAYBACK"));
        System.out.println(HKBTxnUtil.hkbPayBackToJF(map));
        ;
    }
}
