package com.cn.thinkx.wecard.facade.telrecharge.service.impl;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.wecard.facade.telrecharge.mapper.TelChannelInfMapper;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelInf;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelInfFacade;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("telChannelInfFacade")
public class TelChannelInfFacadeImpl  implements TelChannelInfFacade {

	@Autowired
	private TelChannelInfMapper telChannelInfMapper;
	
	@Override
	public TelChannelInf getTelChannelInfById(String channelId) throws Exception {
		return telChannelInfMapper.getById(channelId);
	}

	@Override
	public int saveTelChannelInf(TelChannelInf telChannelInf) throws Exception {
		 return telChannelInfMapper.insert(telChannelInf);
	}

	@Override
	public int updateTelChannelInf(TelChannelInf telChannelInf) throws Exception {
		return telChannelInfMapper.update(telChannelInf);
	}

	@Override
	public int deleteTelChannelInfById(String channelId) throws Exception {
		return telChannelInfMapper.deleteById(channelId);
	}

	@Override
	public List<TelChannelInf> getTelChannelInfList(TelChannelInf telChannelInf) throws Exception {
		return telChannelInfMapper.getList(telChannelInf);
	}
	
	/**
	 * 扣减的渠道金额
	 * @param payAmt 订单金额，需扣减的金额
	 * @return
	 */
	public int subChannelReserveAmt(String channelId,BigDecimal payAmt) throws Exception{
		TelChannelInf telChannelInf=this.getTelChannelInfById(channelId);
		if(telChannelInf.getChannelReserveAmt().compareTo(payAmt) ==-1){
			return 0;
		}
		BigDecimal currReserveAmt =telChannelInf.getChannelReserveAmt().subtract(payAmt).setScale(3, BigDecimal.ROUND_DOWN);
		telChannelInf.setChannelReserveAmt(currReserveAmt);
		return this.updateTelChannelInf(telChannelInf);
	}

	@Override
	public PageInfo<TelChannelInf> getTelChannelInfPage(int startNum, int pageSize, TelChannelInf entity) throws Exception {
		PageHelper.startPage(startNum, pageSize);
		List<TelChannelInf> list = getTelChannelInfList(entity);
		PageInfo<TelChannelInf> page = new PageInfo<TelChannelInf>(list);
		return page;
	}

	@Override
	public TelChannelInf getTelChannelInfByMchntCode(String mchntCode) throws Exception {
		return telChannelInfMapper.getTelChannelInfByMchntCode(mchntCode);
	}
}
