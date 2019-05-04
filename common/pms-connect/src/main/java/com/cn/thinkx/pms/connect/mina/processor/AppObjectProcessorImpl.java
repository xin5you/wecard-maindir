package com.cn.thinkx.pms.connect.mina.processor;

import java.math.BigDecimal;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.pms.base.utils.ReadPropertiesFile;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pms.connect.entity.BizMessageObj;
import com.cn.thinkx.pms.connect.entity.CommMessage;
import com.cn.thinkx.pms.connect.mina.AppObjectProcessor;

/**
 * bytes object 转化
 * 
 * @author sunyue
 * 
 */
@Service
public class AppObjectProcessorImpl implements AppObjectProcessor {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final byte[] msgTop1 = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 };
	public static final byte[] msgTop2 = new byte[] { 4, 0, 0, 0, 0, 0, 0, 0, 17, 0, 0, 0, -1, -1, -1, -1, 0, 0, -5, 30 };
	// acqdom 97,99,113,100,111,109,
	// vCardBatInq 118,67,97,114,100,113,
	public static final byte[] msgTop3 = new byte[] { 0, 0 };
	public static final String len = "000";
	public static final String otherDataLen = "000000";
	static final String sep = "\r\n";

	public Object msg2obj(byte[] messageBytes) throws Exception {
		BizMessageObj message = new BizMessageObj();
		// 报文头 长度104 报文域长度的长度为10
		byte[] bodyLenByte = new byte[10];
		System.arraycopy(messageBytes, 104, bodyLenByte, 0, 10);
		int bodyLen = Integer.parseInt(new String(bodyLenByte).trim());
		// logger.info("bodyLen:" + bodyLen);
		if (bodyLen > 0) {
			byte[] bodyByte = new byte[bodyLen - 10];
			System.arraycopy(messageBytes, 114, bodyByte, 0, bodyLen - 10);

			int len = 0;
			int fieldsIndex = 0;
			byte[] lenByte;
			// msgHead
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setMsgHead("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setMsgHead(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("msgHead:" + message.getMsgHead());
			// dataHead
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setDataHead("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setDataHead(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("dataHead:" + message.getDataHead());
			// txnType
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setTxnType("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setTxnType(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("txnType:" + message.getTxnType());
			// swtTxnDate
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setSwtTxnDate("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setSwtTxnDate(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("swtTxnDate:" + message.getSwtTxnDate());
			// swtTxnTime
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setSwtTxnTime("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setSwtTxnTime(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("swtTxnTime:" + message.getSwtTxnTime());
			// swtSettleDate
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setSwtSettleDate("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setSwtSettleDate(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("swtSettleDate:" + message.getSwtSettleDate());
			// channel
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setChannel("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setChannel(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("channel:" + message.getChannel());
			// swtBatchNo
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setSwtBatchNo("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setSwtBatchNo(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("swtBatchNo:" + message.getSwtBatchNo());
			// swtFlowNo
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setSwtFlowNo("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setSwtFlowNo(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("swtFlowNo:" + message.getSwtFlowNo());
			// recTxnDate
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setRecTxnDate("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setRecTxnDate(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("recTxnDate:" + message.getRecTxnDate());
			// recTxnTime
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setRecTxnTime("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setRecTxnTime(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("recTxnTime:" + message.getRecTxnTime());
			// recBatchNo
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setRecBatchNo("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setRecBatchNo(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("recBatchNo:" + message.getRecBatchNo());
			// recFlowNo
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setRecFlowNo("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setRecFlowNo(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("recFlowNo:" + message.getRecFlowNo());
			// issChannel
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setIssChannel("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setIssChannel(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("issChannel:" + message.getIssChannel());
			// innerMerchantNo
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setInnerMerchantNo("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setInnerMerchantNo(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("innerMerchantNo:" + message.getInnerMerchantNo());
			// innerShopNo
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setInnerShopNo("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setInnerShopNo(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("innerShopNo:" + message.getInnerShopNo());
			// innerPosNo
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setInnerPosNo("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setInnerPosNo(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("innerPosNo:" + message.getInnerPosNo());
			// track2
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setTrack2("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setTrack2(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("track2:" + message.getTrack2());
			// track3
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setTrack3("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setTrack3(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("track3:" + message.getTrack3());
			// cardNo
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setCardNo("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setCardNo(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("cardNo:" + message.getCardNo());
			// cvv2
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setCvv2("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setCvv2(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("cvv2:" + message.getCvv2());
			// expDate
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setExpDate("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setExpDate(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("expDate:" + message.getExpDate());
			// accType
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setAccType("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setAccType(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("accType:" + message.getAccType());
			// txnAmount
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setTxnAmount("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setTxnAmount(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("txnAmount:" + message.getTxnAmount());
			// cardHolderFee
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setCardHolderFee("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setCardHolderFee(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("cardHolderFee:" + message.getCardHolderFee());
			// balance
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setBalance("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setBalance(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("balance:" + message.getBalance());
			// pinQuiry
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setPinQuiry("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setPinQuiry(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("pinQuiry:" + message.getPinQuiry());
			// pinQuiryNew
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setPinQuiryNew("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setPinQuiryNew(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("pinQuiryNew:" + message.getPinQuiryNew());
			// pinTxn
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setPinTxn("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setPinTxn(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("pinTxn:" + message.getPinTxn());
			// pinTxnNew
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setPinTxnNew("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setPinTxnNew(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("pinTxnNew:" + message.getPinTxnNew());
			// respCode
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setRespCode("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setRespCode(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("respCode:" + message.getRespCode());
			// authCode
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setAuthCode("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setAuthCode(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("authCode:" + message.getAuthCode());
			// referenceNo
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setReferenceNo("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setReferenceNo(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("referenceNo:" + message.getReferenceNo());
			// oriSwtBatchNo
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setOriSwtBatchNo("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setOriSwtBatchNo(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("oriSwtBatchNo:" + message.getOriSwtBatchNo());
			// oriSwtFlowNo
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setOriSwtFlowNo("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setOriSwtFlowNo(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("oriSwtFlowNo:" + message.getOriSwtFlowNo());
			// oriRecBatchNo
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setOriRecBatchNo("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setOriRecBatchNo(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("oriRecBatchNo:" + message.getOriRecBatchNo());
			// oriRecFlowNo
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setOriRecFlowNo("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setOriRecFlowNo(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("oriRecFlowNo:" + message.getOriRecFlowNo());
			// oriTxnAmount
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setOriTxnAmount("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setOriTxnAmount(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("oriTxnAmount:" + message.getOriTxnAmount());
			// oriCardHolderFee
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setOriCardHolderFee("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setOriCardHolderFee(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("oriCardHolderFee:" + message.getOriCardHolderFee());
			// filePath
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setFilePath("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setFilePath(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("filePath:" + message.getFilePath());
			// resv1
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setResv1("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setResv1(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("resv1:" + message.getResv1());
			// resv2
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setResv2("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setResv2(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("resv2:" + message.getResv2());
			// resv3
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setResv3("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setResv3(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("resv3:" + message.getResv3());
			// resv4
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setResv4("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setResv4(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("resv4:" + message.getResv4());
			// mac
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setMac("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setMac(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("mac:" + message.getMac());
			// packageNo
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setPackageNo("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setPackageNo(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("packageNo:" + message.getPackageNo());
			// resv5
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setResv5("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setResv5(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("resv5:" + message.getResv5());
			// resv6
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setResv6("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setResv6(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("resv6:" + message.getResv6());
			// resv7
			lenByte = new byte[3];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 3);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 3;
			if (len == 0) {
				message.setResv7("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setResv7(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("resv7:" + message.getResv7());
			// otherdata
			lenByte = new byte[6];
			System.arraycopy(bodyByte, fieldsIndex, lenByte, 0, 6);
			len = Integer.parseInt(new BigDecimal(new String(lenByte)).toString());
			fieldsIndex += 6;
			if (len == 0) {
				message.setOtherdata("");
			} else {
				byte[] msgByte = new byte[len];
				System.arraycopy(bodyByte, fieldsIndex, msgByte, 0, len);
				message.setOtherdata(new String(msgByte, "gbk"));
				fieldsIndex += len;
			}
			// logger.info("otherdata:" + message.getOtherdata());
		}
		logger.info("prepared msg：" + JSONArray.toJSONString(message)); 
		return message;
	}

	public byte[] obj2msg(Object messageObject) throws Exception {
		// 发送报文
		byte[] msgBag = null;
		CommMessage commMessage = (CommMessage) messageObject;
		BizMessageObj message = (BizMessageObj) commMessage.getMessageObject();
		String msg = "";
		// logger.info("##########fields.length#################");
		if (null != message) {
			// 正常业务报文
			if (message.getPackageNo() != null && !message.getPackageNo().equals("")) {
				String value = "";
				// msgHead
				value = message.getMsgHead();
				msg += this.combine(value);
				// logger.info("msgHead:" + value);
				// dataHead
				value = message.getDataHead();
				msg += this.combine(value);
				// logger.info("dataHead:" + value);
				// txnType
				value = message.getTxnType();
				msg += this.combine(value);
				// logger.info("txnType:" + value);
				// swtTxnDate
				value = message.getSwtTxnDate();
				msg += this.combine(value);
				// logger.info("swtTxnDate:" + value);
				// swtTxnTime
				value = message.getSwtTxnTime();
				msg += this.combine(value);
				// logger.info("swtTxnTime:" + value);
				// swtSettleDate
				value = message.getSwtSettleDate();
				msg += this.combine(value);
				// logger.info("swtSettleDate:" + value);
				// channel
				value = message.getChannel();
				msg += this.combine(value);
				// logger.info("channel:" + value);
				// swtBatchNo
				value = message.getSwtBatchNo();
				msg += this.combine(value);
				// logger.info("swtBatchNo:" + value);
				// swtFlowNo
				value = message.getSwtFlowNo();
				msg += this.combine(value);
				// logger.info("swtFlowNo:" + value);
				// recTxnDate
				value = message.getRecTxnDate();
				msg += this.combine(value);
				// logger.info("recTxnDate:" + value);
				// recTxnTime
				value = message.getRecTxnTime();
				msg += this.combine(value);
				// logger.info("recTxnTime:" + value);
				// recBatchNo
				value = message.getRecBatchNo();
				msg += this.combine(value);
				// logger.info("recBatchNo:" + value);
				// recFlowNo
				value = message.getRecFlowNo();
				msg += this.combine(value);
				// logger.info("recFlowNo:" + value);
				// issChannel
				value = message.getIssChannel();
				msg += this.combine(value);
				// logger.info("issChannel:" + value);
				// innerMerchantNo
				value = message.getInnerMerchantNo();
				msg += this.combine(value);
				// logger.info("innerMerchantNo:" + value);
				// innerShopNo
				value = message.getInnerShopNo();
				msg += this.combine(value);
				// logger.info("innerShopNo:" + value);
				// innerPosNo
				value = message.getInnerPosNo();
				msg += this.combine(value);
				// logger.info("innerPosNo:" + value);
				// track2
				value = message.getTrack2();
				msg += this.combine(value);
				// logger.info("track2:" + value);
				// track3
				value = message.getTrack3();
				msg += this.combine(value);
				// logger.info("track3:" + value);
				// cardNo
				value = message.getCardNo();
				msg += this.combine(value);
				// logger.info("cardNo:" + value);
				// cvv2
				value = message.getCvv2();
				msg += this.combine(value);
				// logger.info("cvv2:" + value);
				// expDate
				value = message.getExpDate();
				msg += this.combine(value);
				// logger.info("expDate:" + value);
				// accType
				value = message.getAccType();
				msg += this.combine(value);
				// logger.info("accType:" + value);
				// txnAmount
				value = message.getTxnAmount();
				msg += this.combine(value);
				// logger.info("txnAmount:" + value);
				// cardHolderFee
				value = message.getCardHolderFee();
				msg += this.combine(value);
				// logger.info("cardHolderFee:" + value);
				// balance
				value = message.getBalance();
				msg += this.combine(value);
				// logger.info("balance:" + value);
				// pinQuiry
				value = message.getPinQuiry();
				msg += this.combine(value);
				// logger.info("pinQuiry:" + value);
				// pinQuiryNew
				value = message.getPinQuiryNew();
				msg += this.combine(value);
				// logger.info("pinQuiryNew:" + value);
				// pinTxn
				value = message.getPinTxn();
				msg += this.combine(value);
				// logger.info("pinTxn:" + value);
				// pinTxnNew
				value = message.getPinTxnNew();
				msg += this.combine(value);
				// logger.info("pinTxnNew:" + value);
				// respCode
				value = message.getRespCode();
				msg += this.combine(value);
				// logger.info("respCode:" + value);
				// authCode
				value = message.getAuthCode();
				msg += this.combine(value);
				// logger.info("authCode:" + value);
				// referenceNo
				value = message.getReferenceNo();
				msg += this.combine(value);
				// logger.info("referenceNo:" + value);
				// oriSwtbatchNo
				value = message.getOriSwtBatchNo();
				msg += this.combine(value);
				// logger.info("oriSwtbatchNo:" + value);
				// oriSwtflowNo
				value = message.getOriSwtFlowNo();
				msg += this.combine(value);
				// logger.info("oriSwtflowNo:" + value);
				// oriRecBatchNo
				value = message.getOriRecBatchNo();
				msg += this.combine(value);
				// logger.info("oriRecBatchNo:" + value);
				// oriRecFlowNo
				value = message.getOriRecFlowNo();
				msg += this.combine(value);
				// logger.info("oriRecFlowNo:" + value);
				// oriTxnAmount
				value = message.getOriTxnAmount();
				msg += this.combine(value);
				// logger.info("oriTxnAmount:" + value);
				// oriCardHolderFee
				value = message.getOriCardHolderFee();
				msg += this.combine(value);
				// logger.info("oriCardHolderFee:" + value);
				// filePath
				value = message.getFilePath();
				msg += this.combine(value);
				// logger.info("filePath:" + value);
				// resv1
				value = message.getResv1();
				msg += this.combine(value);
				// logger.info("resv1:" + value);
				// resv2
				value = message.getResv2();
				msg += this.combine(value);
				// logger.info("resv2:" + value);
				// resv3
				value = message.getResv3();
				msg += this.combine(value);
				// logger.info("resv3:" + value);
				// resv4
				value = message.getResv4();
				msg += this.combine(value);
				// logger.info("resv4:" + value);
				// mac
				value = message.getMac();
				msg += this.combine(value);
				// logger.info("mac:" + value);
				// packageNo
				value = message.getPackageNo();
				msg += this.combine(value);
				// logger.info("packageNo:" + value);
				// resv5
				value = message.getResv5();
				msg += this.combine(value);
				// logger.info("resv5:" + value);
				// resv6
				value = message.getResv6();
				msg += this.combine(value);
				// logger.info("resv6:" + value);
				// resv7
				value = message.getResv7();
				msg += this.combine(value);
				// logger.info("resv7:" + value);
				// otherdata
				value = message.getOtherdata();
				if (null == value || "".equals(value)) {
					msg += otherDataLen;
				} else {
					msg += StringUtil.fillString(String.valueOf(value.length()), 6) + value;
				}
				// logger.info("otherdata:" + value);

				// 拼接报文头
				byte[] msgTop = this.combineMsgTop(message.getServiceName(), msg);
				// 拼接报文
				msgBag = this.combineMsg(msg, msgTop);
				String ss = new String(msgBag, Charset.forName("UTF-8"));
				logger.info("java TO C message：" + ss);
			}
		}

		return msgBag;
	}

	// 组装报文头
	public byte[] combineMsgTop(String serviceName, String message) {
		String domanid = ReadPropertiesFile.getInstance().getProperty("DOMAN1_ID", null);
		// 报文域总长度（包含10位报文域长度）
		byte[] msgTotalLen = StringUtil.StringTobyte(message.getBytes().length + 10);
		byte[] doman = StringUtil.fillByteArray(domanid.getBytes(), 33);
		byte[] service = StringUtil.fillByteArray(serviceName.getBytes(), 33);
		/**
		 * 报文头(总长度111) msgTop1+报文域总长度8位+msgTop2+domanid+serviceName+msgTop3
		 */
		byte[] msgTop = new byte[104];
		// for (int i = 0; i < msgTop.length; i++) {
		// msgTop[i] = 48;
		// }
		int topLength = 0;
		System.arraycopy(msgTop1, 0, msgTop, topLength, msgTop1.length);
		topLength += msgTop1.length;
		System.arraycopy(msgTotalLen, 0, msgTop, topLength, msgTotalLen.length);
		topLength += msgTotalLen.length;
		System.arraycopy(msgTop2, 0, msgTop, topLength, msgTop2.length);
		topLength += msgTop2.length;
		System.arraycopy(doman, 0, msgTop, topLength, doman.length);
		topLength += doman.length;
		System.arraycopy(service, 0, msgTop, topLength, service.length);
		topLength += service.length;
		System.arraycopy(msgTop3, 0, msgTop, topLength, msgTop3.length);
		logger.info("msgTop：" + StringUtil.bytesToHexString(msgTop));
		return msgTop;
	}

	/**
	 * 组装报文域
	 * 
	 * @param value
	 * @return
	 */
	public String combine(String value) {
		String msg = "";
		if (null == value || "".equals(value)) {
			msg += len;
		} else {
			msg += StringUtil.fillString(String.valueOf(value.getBytes().length), 3) + value;
		}
		return msg;
	}

	/**
	 * 组装报文
	 * 
	 * @param message
	 *            报文域数据
	 * @param msgTop
	 *            报文头
	 * @return
	 */
	public byte[] combineMsg(String message, byte[] msgTop) {
		// 报文域数据
		byte[] a = message.getBytes();
		/**
		 * 报文格式 报文头 10位报文域长度 报文域数据
		 */
		// 报文总长度
		int totalLen = msgTop.length + 10 + a.length;

		// 10位报文域长度（包含自己）
		byte[] dataLen = StringUtil.fillString(String.valueOf(a.length + 10), 10).getBytes();
		// 报文
		byte[] msg = new byte[totalLen];
		System.arraycopy(msgTop, 0, msg, 0, msgTop.length);
		System.arraycopy(dataLen, 0, msg, msgTop.length, 10);
		System.arraycopy(a, 0, msg, msgTop.length + 10, a.length);
		return msg;
	}

}
