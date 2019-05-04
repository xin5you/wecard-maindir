package com.cn.thinkx.wecard.facade.telrecharge.service.impl;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelReserveType;
import com.cn.thinkx.wecard.facade.telrecharge.mapper.TelChannelInfMapper;
import com.cn.thinkx.wecard.facade.telrecharge.mapper.TelChannelReserveDetailMapper;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelInf;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelReserveDetail;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelReserveDetailFacade;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("telChannelReserveDetailFacade")
public class TelChannelReserveDetailServiceImpl  implements TelChannelReserveDetailFacade {

	@Autowired
	private TelChannelReserveDetailMapper telChannelReserveDetailMapper;
	
	@Autowired
	private TelChannelInfMapper telChannelInfMapper;
	
	@Override
	public TelChannelReserveDetail getTelChannelReserveDetailById(String id) throws Exception {
		return telChannelReserveDetailMapper.getById(id);
	}

	@Override
	public int saveTelChannelReserveDetail(TelChannelReserveDetail telChannelReserveDetail) throws Exception {
		 return telChannelReserveDetailMapper.insert(telChannelReserveDetail);
	}

	@Override
	public int updateTelChannelReserveDetail(TelChannelReserveDetail telChannelReserveDetail) throws Exception {
		return telChannelReserveDetailMapper.update(telChannelReserveDetail);
	}

	@Override
	public int deleteTelChannelReserveDetailById(String id) throws Exception {
		return telChannelReserveDetailMapper.deleteById(id);
	}

	@Override
	public List<TelChannelReserveDetail> getTelChannelReserveDetailList(TelChannelReserveDetail telChannelReserveDetail)
			throws Exception {
		return telChannelReserveDetailMapper.getList(telChannelReserveDetail);
	}

	@Override
	public PageInfo<TelChannelReserveDetail> getTelChannelReserveDetailPage(int startNum, int pageSize, TelChannelReserveDetail entity) throws Exception {
		PageHelper.startPage(startNum, pageSize);
		List<TelChannelReserveDetail> list = getTelChannelReserveDetailList(entity);
		for (TelChannelReserveDetail telChannelReserveDetail : list) {
			telChannelReserveDetail.setReserveType(ChannelReserveType.findByCode(telChannelReserveDetail.getReserveType()));
		}
		PageInfo<TelChannelReserveDetail> page = new PageInfo<TelChannelReserveDetail>(list);
		return page;
	}

	@Override
	public boolean updateTelChannelInfReserve(TelChannelReserveDetail telChannelReserveDetail) throws Exception {
		int i = this.saveTelChannelReserveDetail(telChannelReserveDetail);
		if (i != 1) {
			throw new RuntimeException();
		}
		TelChannelInf telChannelInf = telChannelInfMapper.getById(telChannelReserveDetail.getChannelId());
		
		BigDecimal currReserveAmt = null ;
		if(ChannelReserveType.ChannelReserveType1.getCode().equals(telChannelReserveDetail.getReserveType())){
			currReserveAmt = telChannelInf.getChannelReserveAmt().add(telChannelReserveDetail.getReserveAmt()).setScale(3, BigDecimal.ROUND_DOWN);
		}
		if(ChannelReserveType.ChannelReserveType2.getCode().equals(telChannelReserveDetail.getReserveType())){
			if(telChannelInf.getChannelReserveAmt().compareTo(telChannelReserveDetail.getReserveAmt()) ==-1){
				throw new RuntimeException();
			}
			currReserveAmt = telChannelInf.getChannelReserveAmt().subtract(telChannelReserveDetail.getReserveAmt()).setScale(3, BigDecimal.ROUND_DOWN);
		}
		telChannelInf.setChannelReserveAmt(currReserveAmt);
		int j = telChannelInfMapper.update(telChannelInf);
		if (j != 1) {
			throw new RuntimeException();
		}
		return true;
	}
}
