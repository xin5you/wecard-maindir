package com.cn.thinkx.pms.connect.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.thinkx.pms.base.utils.ReadPropertiesFile;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pms.connect.entity.BizMessageObj;
import com.cn.thinkx.pms.connect.entity.CommMessage;
import com.cn.thinkx.pms.connect.entity.TxnPackageDTO;
import com.cn.thinkx.pms.connect.service.Java2TXNBusinessService;
import com.cn.thinkx.pms.connect.utils.ConnectConstant;

public class Java2TXNBusinessServiceImpl implements Java2TXNBusinessService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private ManagedAsyn2SynClient managedAsyn2SynClient;

	public Boolean heartBeatTransation(TxnPackageDTO txnPackageDTO) {
		CommMessage sendMsg = new CommMessage();
		Map<String, String> params = new HashMap<String, String>();
		params.put(ConnectConstant.TXN_TYPE, 
				ReadPropertiesFile.getInstance().getProperty("HEART_BEAT_TXN_TYPE", null));
		params.put(ConnectConstant.TXN_CHANNEL, 
				ReadPropertiesFile.getInstance().getProperty("HEART_BEAT_TXN_CHNL_ID", null));
		BizMessageObj obj = initMessageObj(params);
		sendMsg.setMessageObject(obj);
		// 发送报文 JAVA-->C
		try {
			sendMsg = managedAsyn2SynClient.sendMessage(sendMsg);
			return true;
		} catch (Exception e) {
			logger.error(this.getClass().getName(), e);
			return false;
		}
	}

	// 交易查询接口（针对单卡消费）
	public TxnPackageDTO queryTransation(TxnPackageDTO txnPackageDTO, Map<String, String> params) throws Exception {
		CommMessage sendMsg = new CommMessage();
		try {
			// 组装报文
			BizMessageObj obj = initMessageObj(params);
			// 原交易类型
			obj.setCvv2(txnPackageDTO.getOldTxnType());
			// 收单机构
			obj.setDataHead(txnPackageDTO.getConsumerCode());
			// 商户代码
			obj.setInnerMerchantNo(txnPackageDTO.getMerchantCode());
			// 门店号的值取决于商户是否传过来，如果没传过来，则内部传特殊的门店号：999999。
			if (StringUtil.isNotBlank(txnPackageDTO.getShopCode())) {
				obj.setInnerPosNo(txnPackageDTO.getShopCode());
			} else {
				obj.setInnerPosNo("999999");
			}
			// 订单号
			obj.setTrack2(txnPackageDTO.getOrderId());
			// 交易日期
			obj.setSwtTxnDate(txnPackageDTO.getTxnDate());
			// 交易时间
			obj.setSwtTxnTime(txnPackageDTO.getTxnTime());
			sendMsg.setMessageObject(obj);
			// 发送报文 JAVA-->C
			try {
				sendMsg = managedAsyn2SynClient.sendMessage(sendMsg);
			} catch (Exception e) {
				logger.error("C后台未启动！！",e);
				throw e;
			}
			// 解析返回值C-->JAVA
			obj = (BizMessageObj) sendMsg.getMessageObject();
			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			String allRecord = obj.getResv1();
			logger.debug("allRecord:" + allRecord);

			if (!"".equals(allRecord)) {
				// 解析所有的记录 ，以“|”截取
				String[] records = allRecord.split("\\|");
				logger.debug("recordsLength: " + records.length + " records：  " + records);
				if (records != null && records.length > 0) {
					for (int i = 0; i < records.length; i++) {
						String record = records[i];
						logger.debug(record);
						String[] field = record.split("\\^", -1);
						txnPackageDTO.setRspCode(respCode);
						txnPackageDTO.setTxnType(field[0]);
						txnPackageDTO.setOldTxnType(field[1]);
						txnPackageDTO.setMerchantCode(field[2]);
						txnPackageDTO.setShopCode(field[3]);
						txnPackageDTO.setOrderId(field[4]);
						txnPackageDTO.setTxnDate(field[5]);
						txnPackageDTO.setTxnTime(field[6]);
						/** 只有登记成功的才返回以下几个域 **/
						if (ConnectConstant.RESPONSE_SUCCESS_CODE.equals(respCode)) {
							txnPackageDTO.setAmount(field[7]);
							txnPackageDTO.setCurType(field[8]);
							txnPackageDTO.setCardNO(field[9]);
							txnPackageDTO.setCardHolder(field[10]);
							txnPackageDTO.setProductInfo(field[11]);
							txnPackageDTO.setRemark(field[12]);
							txnPackageDTO.setMerchantName(field[13]);
							txnPackageDTO.setConsumerCode(field[14]);
							txnPackageDTO.setMerchantURL(field[15]);
							txnPackageDTO.setSequenceNo(field[16]);
							txnPackageDTO.setSettleDate(field[17]);
						}
					}
				}
			} else {
				txnPackageDTO.setRspCode(respCode);
			}
		} catch (Exception e) {
			logger.error(this.getClass().getName(),e);
			throw e;
		}
		return txnPackageDTO;
	}

	/**
	 * 支付网关 消费
	 * 
	 * @param txnPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public TxnPackageDTO consume(TxnPackageDTO txnPackageDTO, Map<String, String> params) throws Exception {
		CommMessage sendMsg = new CommMessage();
		try {
			BizMessageObj obj = initMessageObj(params);
			// // 组装报文
			// obj.setTxnType("G100");
			// // 渠道号
			// obj.setChannel(properties.getProperty("TXN_CHNL_ID"));
			// obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			// // 服务名
			// obj.setServiceName(ConnectConstant.VTXN);
			// 原交易类型
			obj.setCvv2(txnPackageDTO.getOldTxnType());
			// 收单机构
			obj.setDataHead(txnPackageDTO.getConsumerCode());
			// 商户代码
			obj.setInnerMerchantNo(txnPackageDTO.getMerchantCode());
			// 门店号的值取决于商户是否传过来，如果没传过来，则内部传特殊的门店号：999999。
			if (StringUtil.isNotEmpty(txnPackageDTO.getShopCode())) {
				obj.setInnerPosNo(txnPackageDTO.getShopCode());
			} else {
				obj.setInnerPosNo("999999");
			}
			// 订单号
			obj.setTrack2(txnPackageDTO.getOrderId());
			// 交易日期
			obj.setSwtTxnDate(txnPackageDTO.getTxnDate());
			// 交易时间
			obj.setSwtTxnTime(txnPackageDTO.getTxnTime());
			// 交易金额
			obj.setTxnAmount(txnPackageDTO.getAmount());
			// 交易币种
			obj.setSwtBatchNo(txnPackageDTO.getCurType());
			// 订货人姓名
			if (StringUtil.isNotEmpty(txnPackageDTO.getCardHolder())) {
				obj.setFilePath(txnPackageDTO.getCardHolder());
			}
			// 商品信息
			if (StringUtil.isNotBlank(txnPackageDTO.getProductInfo())) {
				obj.setResv2(txnPackageDTO.getProductInfo());
			}
			// 附加信息
			if (StringUtil.isNotBlank(txnPackageDTO.getRemark())) {
				obj.setResv3(txnPackageDTO.getRemark());
			}
			sendMsg.setMessageObject(obj);
			// 发送报文 JAVA-->C
			try {
				sendMsg = managedAsyn2SynClient.sendMessage(sendMsg);
			} catch (Exception e) {
				logger.error(this.getClass().getName(),e);
				throw new Exception("C后台未启动！！");
			}
			// 解析返回值C-->JAVA
			obj = (BizMessageObj) sendMsg.getMessageObject();
			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);

			String allRecord = obj.getResv1();
			logger.info("allRecord:" + allRecord);
			// G120^700000000000001^999999^2011101017304101^20111010^173041^1002^156^^^^
			if (!"".equals(allRecord)) {
				// 解析所有的记录 ，以“|”截取
				String[] records = allRecord.split("\\|");
				logger.info("recordsLength: " + records.length + " records：  " + records);
				if (records != null && records.length > 0) {
					String record = records[0];
					logger.info(record);
					String[] field = record.split("\\^", -1);
					txnPackageDTO.setRspCode(respCode);
					txnPackageDTO.setTxnType(field[0]);
					txnPackageDTO.setMerchantCode(field[1]);
					txnPackageDTO.setShopCode(field[2]);
					txnPackageDTO.setOrderId(field[3]);
					txnPackageDTO.setTxnDate(field[4]);
					txnPackageDTO.setTxnTime(field[5]);
					txnPackageDTO.setAmount(field[6]);
					txnPackageDTO.setCurType(field[7]);
					txnPackageDTO.setCardHolder(field[8]);
					txnPackageDTO.setProductInfo(field[9]);
					txnPackageDTO.setRemark(field[10]);
					/** 只有登记成功的才返回以下几个域 **/
					if (ConnectConstant.RESPONSE_SUCCESS_CODE.equals(respCode)) {
						txnPackageDTO.setMerchantName(field[11]);
						txnPackageDTO.setConsumerCode(field[12]);
						txnPackageDTO.setMerchantURL(field[13]);
						txnPackageDTO.setSequenceNo(field[14]);
						txnPackageDTO.setSettleDate(field[15]);
					}
				}
			} else {
				txnPackageDTO.setRspCode(respCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txnPackageDTO;
	}

	@Override
	public TxnPackageDTO reversal(TxnPackageDTO txnPackageDTO, Map<String, String> params) throws Exception {
		CommMessage sendMsg = new CommMessage();
		logger.info("############消 费 冲 正############");
		try {
			BizMessageObj obj = initMessageObj(params);
			// 原交易流水号
			obj.setReferenceNo(txnPackageDTO.getOrigTxnNo());
			// 交易流水号
			obj.setSwtFlowNo(txnPackageDTO.getTxnNo());
			// 卡号
			obj.setCardNo(txnPackageDTO.getCardNO());
			// 消费金额
			obj.setTxnAmount(txnPackageDTO.getAmount());
			// 商户号
			obj.setInnerMerchantNo(txnPackageDTO.getMerchantCode());
			sendMsg.setMessageObject(obj);
			// 发送报文 JAVA-->C
			logger.info("begin java------->c");
			try {
				sendMsg = managedAsyn2SynClient.sendMessage(sendMsg);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("C后台未启动！！");
			}
			// 解析返回值C-->JAVA
			logger.info("begin c------->java");
			obj = (BizMessageObj) sendMsg.getMessageObject();
			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			String allRecord = obj.getBalance();
			logger.debug("allRecord:" + allRecord);
			if ("00".equals(respCode)) {
				logger.debug("消 费 冲 正 成 功！");
				txnPackageDTO.setRspCode(respCode);
			} else {
				txnPackageDTO.setRspCode(respCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txnPackageDTO;

	}

	@Override
	public TxnPackageDTO reCharge(TxnPackageDTO txnPackageDTO, Map<String, String> params) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	private BizMessageObj initMessageObj(Map<String, String> params) {
		// 组装报文
		BizMessageObj msgObj = new BizMessageObj();
		msgObj.setTxnType(params.get(ConnectConstant.TXN_TYPE));
		msgObj.setChannel(params.get(ConnectConstant.TXN_CHANNEL));
		msgObj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
		msgObj.setServiceName(ConnectConstant.VTXN);
		return msgObj;
	}

	public void setManagedAsyn2SynClient(ManagedAsyn2SynClient managedAsyn2SynClient) {
		this.managedAsyn2SynClient = managedAsyn2SynClient;
	}

}
