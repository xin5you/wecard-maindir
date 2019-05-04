package com.cn.thinkx.wecard.facade.telrecharge.service.impl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.activemq.service.RechargeMobileProducerService;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.providerOrderRechargeState;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.facade.telrecharge.mapper.TelChannelOrderInfMapper;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelInf;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelOrderInf;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelOrderInfUpload;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelProductInf;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelProviderOrderInf;
import com.cn.thinkx.wecard.facade.telrecharge.resp.TeleRespDomain;
import com.cn.thinkx.wecard.facade.telrecharge.resp.TeleRespVO;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelInfFacade;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelOrderInfFacade;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelProductInfFacade;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelProviderOrderInfFacade;
import com.cn.thinkx.wecard.facade.telrecharge.utils.ResultsUtil;
import com.cn.thinkx.wecard.facade.telrecharge.utils.TeleConstants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("telChannelOrderInfFacade")
public class TelChannelOrderInfServiceImpl  implements TelChannelOrderInfFacade {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TelChannelOrderInfMapper telChannelOrderInfMapper;
	
	@Autowired
	private TelChannelInfFacade telChannelInfFacade;
	
	@Autowired
	private TelChannelProductInfFacade telChannelProductInfFacade;
	
	@Autowired
	private TelProviderOrderInfFacade telProviderOrderInfFacade;
	
	@Autowired
	private RechargeMobileProducerService rechargeMobileProducerService;
	
	@Override
	public TelChannelOrderInf getTelChannelOrderInfById(String channelOrderId) throws Exception {
		return telChannelOrderInfMapper.getById(channelOrderId);
	}

	@Override
	public int saveTelChannelOrderInf(TelChannelOrderInf telChannelOrderInf) throws Exception {
		 return telChannelOrderInfMapper.insert(telChannelOrderInf);
	}

	@Override
	public int updateTelChannelOrderInf(TelChannelOrderInf telChannelOrderInf) throws Exception {
		return telChannelOrderInfMapper.update(telChannelOrderInf);
	}

	@Override
	public int deleteTelChannelOrderInfById(String channelOrderId) throws Exception {
		return telChannelOrderInfMapper.deleteById(channelOrderId);
	}
	
	/**
	 * 
	 * @param telChannelOrderInf 分销商订单
	 * @param operId 运营商
	 * @param areaName 地区名称
	 * @return
	 * @throws Exception
	 */
	public TeleRespDomain proChannelOrder(TelChannelOrderInf telChannelOrderInf,String operId,String areaName) throws Exception{
		
		//获取分销商信息
		TelChannelInf telChannelInf= telChannelInfFacade.getTelChannelInfById(telChannelOrderInf.getChannelId());
		if(telChannelInf ==null){
			return ResultsUtil.error("110001", "渠道号不存在");
		}
		
		//获取分销商的产品 & 折扣率
		Map maps=new HashMap<>();
		maps.put("productId", telChannelOrderInf.getProductId());
		maps.put("operId", operId);
//		maps.put("areaName", areaName);
		maps.put("channelId", telChannelInf.getChannelId()); //分销商ID
		maps.put("productAmt", telChannelOrderInf.getRechargeValue());
		maps.put("productType", telChannelOrderInf.getRechargeType());
		TelChannelProductInf telChannelProductInf =telChannelProductInfFacade.getProductRateByMaps(maps);
		if(telChannelProductInf ==null){
			return ResultsUtil.error("110002", "产品不存在");
		}
		
		//产品售价 = 产品的价格 * 折扣率
		BigDecimal	payAmt= telChannelProductInf.getProductPrice().multiply(telChannelProductInf.getChannelRate()).setScale(3, BigDecimal.ROUND_DOWN);
		
//		//判断当前的备付金是否大于当前的充值的产品售价
//		if (telChannelInf.getChannelReserveAmt().compareTo(payAmt)== -1){
//			return ResultsUtil.error("110003", "备付金不足");
//		}
		
		telChannelOrderInf.setPayAmt(payAmt);
		//扣减备付金 
	   //	int resOper=telChannelInfFacade.subChannelReserveAmt(telChannelInf.getChannelId(),payAmt);
		//保存订单o.toString()o.toString()o.toString()
		
			telChannelOrderInf.setDataStat("0"); //
			telChannelOrderInf.setOrderStat(TeleConstants.ChannelOrderPayStat.ORDER_PAY_0.getCode()); //待扣款
			telChannelOrderInf.setItemNum("1");//产品数量
			telChannelOrderInf.setChannelRate(telChannelProductInf.getChannelRate().toString()); //折扣率
			telChannelOrderInf.setNotifyStat(TeleConstants.ChannelOrderNotifyStat.ORDER_NOTIFY_1.getCode());  //处理中
			if(telChannelProductInf !=null){
				telChannelOrderInf.setProductId(telChannelProductInf.getProductId());
			}
			if(StringUtil.isNotEmpty(telChannelOrderInf.getNotifyUrl())){
				telChannelOrderInf.setNotifyFlag("0");
			}else{
				telChannelOrderInf.setNotifyFlag("1");
			}
			int resOper=this.saveTelChannelOrderInf(telChannelOrderInf); //保存分销商订单
			
			if(resOper>0){
				TelProviderOrderInf telProviderOrderInf=new TelProviderOrderInf();
				telProviderOrderInf.setRegOrderAmt(telChannelOrderInf.getRechargeValue()); //充值面额
				telProviderOrderInf.setChannelOrderId(telChannelOrderInf.getChannelOrderId());
				telProviderOrderInf.setRechargeState(TeleConstants.ProviderRechargeState.RECHARGE_STATE_8.getCode()); //待充值
				telProviderOrderInf.setDataStat("0");
				resOper=telProviderOrderInfFacade.saveTelProviderOrderInf(telProviderOrderInf); //保存供应商订单
		}
		if(resOper<0){
			//操作不同步，则回退事物
			throw new RuntimeException();
		}
		
		//返回成功的封装数据
		TeleRespVO respVo=new TeleRespVO();
		respVo.setSaleAmount(telChannelOrderInf.getPayAmt().toString());
		respVo.setChannelOrderId(telChannelOrderInf.getChannelOrderId());
		respVo.setPayState(telChannelOrderInf.getOrderStat());
		respVo.setRechargeState(TeleConstants.ProviderRechargeState.RECHARGE_STATE_0.getCode());
		respVo.setOrderTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		respVo.setFacePrice(telChannelOrderInf.getRechargeValue().toString());
		respVo.setItemNum(telChannelOrderInf.getItemNum());
		respVo.setOuterTid(telChannelOrderInf.getOuterTid());
		return ResultsUtil.success(respVo);
	}
	
	public List<TelChannelOrderInf> getTelChannelOrderInfList(TelChannelOrderInf telChannelOrderInf) throws Exception {
		return telChannelOrderInfMapper.getList(telChannelOrderInf);
	}
	
	public void doRechargeMobileMsg(String channelOrderId){
		rechargeMobileProducerService.sendRechargeMobileMsg(channelOrderId);
	}
	/**
	 *  分销商 根据外部订单查询
	 * @param outerId
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	public TelChannelOrderInf getTelChannelOrderInfByOuterId(String outerId,String channelId) throws Exception{
		return telChannelOrderInfMapper.getTelChannelOrderInfByOuterId(outerId,channelId);
	}
	
	/**
	 * 分销商订单分页列表
	 * @param startNum
	 * @param pageSize
	 * @param telChannelOrderInf
	 * @return
	 * @throws Exception 
	 */
     public PageInfo<TelChannelOrderInf> getTelChannelOrderInfPage(int startNum, int pageSize, TelChannelOrderInf telChannelOrderInf) throws Exception{
    		PageHelper.startPage(startNum, pageSize);
    		List<TelChannelOrderInf> telChannelOrderInfList = getTelChannelOrderInfList(telChannelOrderInf);
    		for (TelChannelOrderInf telChannelOrderInf2 : telChannelOrderInfList) {
				if(!StringUtil.isNullOrEmpty(telChannelOrderInf2.getRechargeType()))
					telChannelOrderInf2.setRechargeType(BaseConstants.phoneRechargeOrderType.findByCode(telChannelOrderInf2.getRechargeType()).getValue());
				if(!StringUtil.isNullOrEmpty(telChannelOrderInf2.getOrderStat()))
					telChannelOrderInf2.setOrderStat(BaseConstants.ChannelOrderStat.findByCode(telChannelOrderInf2.getOrderStat()));
				if(!StringUtil.isNullOrEmpty(telChannelOrderInf2.getNotifyStat()))
					telChannelOrderInf2.setNotifyStat(BaseConstants.ChannelOrderNotifyStat.findByCode(telChannelOrderInf2.getNotifyStat()));
			}
    		PageInfo<TelChannelOrderInf> telChannelOrderInfPage = new PageInfo<TelChannelOrderInf>(telChannelOrderInfList);
    		return telChannelOrderInfPage;
     }

	@Override
	public PageInfo<TelChannelOrderInf> getTelChannelOrderInf(int startNum, int pageSize, TelChannelOrderInf telChannelOrderInf) throws Exception {
		PageHelper.startPage(startNum, pageSize);
		List<TelChannelOrderInf> telChannelOrderInfList = telChannelOrderInfMapper.getTelChannelOrderInfList(telChannelOrderInf);
		for (TelChannelOrderInf telChannelOrderInf2 : telChannelOrderInfList) {
			if(!StringUtil.isNullOrEmpty(telChannelOrderInf2.getRechargeState()))
				telChannelOrderInf2.setRechargeState(providerOrderRechargeState.findByCode(telChannelOrderInf2.getRechargeState()));
		}
		PageInfo<TelChannelOrderInf> telChannelOrderInfPage = new PageInfo<TelChannelOrderInf>(telChannelOrderInfList);
		return telChannelOrderInfPage;
	}

	@Override
	public List<TelChannelOrderInfUpload> getTelChannelOrderInfListToUpload(TelChannelOrderInf order) {
		List<TelChannelOrderInfUpload> list = telChannelOrderInfMapper.getTelChannelOrderInfListToUpload(order);
		for (TelChannelOrderInfUpload telChannelOrderInf2 : list) {
			if(!StringUtil.isNullOrEmpty(telChannelOrderInf2.getRechargeState()))
				telChannelOrderInf2.setRechargeState(providerOrderRechargeState.findByCode(telChannelOrderInf2.getRechargeState()));
		}
		return list;
	}

	@Override
	public TelChannelOrderInf getTelChannelOrderInfCount(TelChannelOrderInf order) {
		return telChannelOrderInfMapper.getTelChannelOrderInfCount(order);
	}
}
