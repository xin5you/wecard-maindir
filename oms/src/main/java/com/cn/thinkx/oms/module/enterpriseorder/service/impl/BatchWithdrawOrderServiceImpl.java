package com.cn.thinkx.oms.module.enterpriseorder.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import com.cn.thinkx.cgb.model.CgbRequestDTO;
import com.cn.thinkx.cgb.service.CgbService;
import com.cn.thinkx.oms.module.enterpriseorder.mapper.BatchWithdrawOrderMapper;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchWithdrawOrder;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchWithdrawOrderDetail;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchWithdrawOrderDetailService;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchWithdrawOrderService;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service("batchWithdrawOrderService")
public class BatchWithdrawOrderServiceImpl implements BatchWithdrawOrderService {

	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BatchWithdrawOrderMapper batchWithdrawOrderMapper;

	@Autowired
	private BatchWithdrawOrderDetailService batchWithdrawOrderDetailService;

	@Override
	public BatchWithdrawOrder getById(String id) {
		return batchWithdrawOrderMapper.getById(id);
	}

  /**
	 * 保存
	 * @param entity
	 * @return
	 */
	public int insertBatchWithdrawOrder(BatchWithdrawOrder entity, List<BatchWithdrawOrderDetail> details){
		//批量订单Id
		Snowflake snowflake=IdUtil.getSnowflake(1,1);
		String batchOrderId=snowflake.nextIdStr();

		BatchWithdrawOrderDetail detail=null;
		for (int i=0;i<details.size();i++){
			detail=details.get(i);
			detail.setDetailId(snowflake.nextIdStr());
			detail.setOrderId(batchOrderId);
			//手续费先为0
			detail.setFee(new BigDecimal(0));
			detail.setDataStat("0");
			batchWithdrawOrderDetailService.insertBatchWithdrawOrderDetail(detail);
		}
		entity.setOrderId(batchOrderId);
		entity.setTotalNum(details.size());
		entity.setStat("00"); //初始化新建
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		entity.setLockVersion(0);
		return batchWithdrawOrderMapper.insertBatchWithdrawOrder(entity);
	}

	@Override
	public int updateBatchWithdrawOrder(BatchWithdrawOrder entity) {
		return batchWithdrawOrderMapper.updateBatchWithdrawOrder(entity);
	}

	@Override
	public int deleteBatchWithdrawOrder(String id) {
		batchWithdrawOrderDetailService.deleteBatchWithdrawOrderDetailByOrderId(id);
		return batchWithdrawOrderMapper.deleteBatchWithdrawOrder(id);
	}

	public List<BatchWithdrawOrder> getBatchWithdrawOrderList(BatchWithdrawOrder entity) {
		return batchWithdrawOrderMapper.getBatchWithdrawOrderList(entity);
	}
	@Override
	public PageInfo<BatchWithdrawOrder> getBatchWithdrawOrderPage(int startNum, int pageSize, BatchWithdrawOrder entity) {
		PageHelper.startPage(startNum, pageSize);
        List<BatchWithdrawOrder> list = getBatchWithdrawOrderList(entity);
        PageInfo<BatchWithdrawOrder> page = new PageInfo<BatchWithdrawOrder>(list);
        return page;
	}

	/**
	 * 代付提交
	 * @param entity
	 */
	@Override
	public void doPaymentBatchWithdrawOrder(BatchWithdrawOrder entity){
			entity.setStat("03"); //代付支付中
			this.updateBatchWithdrawOrder(entity);

			BatchWithdrawOrderDetail detail=new BatchWithdrawOrderDetail();
			detail.setOrderId(entity.getOrderId());
			List<BatchWithdrawOrderDetail> payList=batchWithdrawOrderDetailService.getList(detail);

			JSONObject jsonObject=null;
			CgbRequestDTO cgbRequestDTO=null;

			//代付service
			CgbService cgbService=new CgbService();

			if(payList != null){

				boolean payFlag=false;
				//获取代付总额
				try {
					BigDecimal totalMoney = payList.stream().map(BatchWithdrawOrderDetail::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
					int paysize = payList.size();
					//支付的笔数和查询出来的笔数  && 支付总额 和 统计的金额要完全一致，避免失败
					if (entity.getTotalNum().intValue() == paysize && totalMoney.compareTo(entity.getTotalAmount()) == 0) {
						payFlag=true;
					} else {
						payFlag=false;
						logger.info("## 代付失败 代付订单中 总额:{},笔数：{} 。实际处理代付金额:{}，实际处理笔数:{}", entity.getTotalAmount(), entity.getTotalNum(), totalMoney, paysize);
					}

				}catch (Exception ex){
					logger.info("## 代付检验异常：{}",ex);
				}
				if(payFlag) {
					for (int i = 0; i < payList.size(); i++) {
						detail = payList.get(i);
						detail.setPayTime(new Date());
						detail.setPayChanel("2");

						try {
							cgbRequestDTO = new CgbRequestDTO();
							cgbRequestDTO.setEntSeqNo(detail.getDetailId());
							cgbRequestDTO.setInAccName(detail.getReceiverName()); //收款人姓名
							cgbRequestDTO.setInAcc(detail.getReceiverCardNo());  //收款人银行卡号
							cgbRequestDTO.setInAccBank(detail.getBankName()); //"交通银行" 银行名称
							cgbRequestDTO.setAmount(detail.getAmount().toString()); //金额 单位元
							cgbRequestDTO.setPaymentBankid(detail.getPayeeBankLinesNo()); //联行号
							cgbRequestDTO.setRemark(detail.getBankType()); //銀行摘要 用途
							cgbRequestDTO.setComment(detail.getBankType());
							jsonObject = cgbService.dfPaymentResult(cgbRequestDTO);
						} catch (Exception ex) {
							logger.error("## 代付失败 {}", ex);
						}

						try {
							if (jsonObject != null) {
								detail.setRespCode(StringUtil.nullToString(jsonObject.getJSONObject("BEDC").getJSONObject("Message").getJSONObject("commHead").get("retCode")));

								jsonObject = jsonObject.getJSONObject("BEDC");
								jsonObject = jsonObject.getJSONObject("Message");
								if (jsonObject.get("Body") != null && StringUtil.isNotEmpty(String.valueOf(jsonObject.get("Body")))) {
									jsonObject = jsonObject.getJSONObject("Body");
									if (jsonObject != null) {
										detail.setDmsSerialNo(StringUtil.nullToString(jsonObject.get("traceNo")));
										if (jsonObject.get("handleFee") != null) {
											detail.setFee(new BigDecimal(StringUtil.nullToString(jsonObject.get("handleFee"))));
										}
									}
								}
							}
							batchWithdrawOrderDetailService.updateBatchWithdrawOrderDetail(detail);
						} catch (Exception ex) {
							logger.error("## 更新代付失败 {}", ex);
						}
					}
				}
			}
	}
}
