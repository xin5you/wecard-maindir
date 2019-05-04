package com.cn.iboot.diy.api.trans.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.iboot.diy.api.base.constants.BaseConstants;
import com.cn.iboot.diy.api.base.utils.NumberUtils;
import com.cn.iboot.diy.api.base.utils.StringUtil;
import com.cn.iboot.diy.api.operate.mapper.PositOprStatisticsMapper;
import com.cn.iboot.diy.api.trans.domain.TransLog;
import com.cn.iboot.diy.api.trans.mapper.TransMapper;
import com.cn.iboot.diy.api.trans.service.TransService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("transService")
public class TransServiceImpl implements TransService {
	
	@Autowired
	private TransMapper transMapper;
	
	@Autowired
	private PositOprStatisticsMapper positOprStatisticsMapper;

	@Override
	public List<TransLog> getTransLogCur(Map<String, Object> map) {
		transMapper.getTransLogCur(map);
		return (List<TransLog>) map.get("results");
	}

	@Override
	public PageInfo<TransLog> getTransLogPage(int startNum, int pageSize, Map<String, Object> map) {
		PageHelper.startPage(startNum, pageSize);
		List<TransLog> ssList = getTransLogCur(map);
		PageInfo<TransLog> ssPage = new PageInfo<TransLog>(ssList);
		return ssPage;
	}

	@Override
	public List<TransLog> getTransLogCur(TransLog log) {
//		if(!StringUtil.isNullOrEmpty((log.getTransId()))){
//			if(log.getTransId().equals(BaseConstants.TransCode.CW11.getCode())){
//				log.setTransId(BaseConstants.TransCode.CW10.getCode());
//				log.setTransSt(3);
//			}else if(log.getTransId().equals(BaseConstants.TransCode.CW74.getCode())){
//				log.setTransId(BaseConstants.TransCode.CW71.getCode());
//				log.setTransSt(3);
//			}else{
//				log.setTransSt(1);
//			}
//		}
		return transMapper.getTransLogCur(log);
	}
	
	@Override
	public List<TransLog> getTransLogHis(TransLog log) {
//		if(!StringUtil.isNullOrEmpty((log.getTransId()))){
//			if(log.getTransId().equals(BaseConstants.TransCode.CW11.getCode())){
//				log.setTransId(BaseConstants.TransCode.CW10.getCode());
//				log.setTransSt(3);
//			}else if(log.getTransId().equals(BaseConstants.TransCode.CW74.getCode())){
//				log.setTransId(BaseConstants.TransCode.CW71.getCode());
//				log.setTransSt(3);
//			}else{
//				log.setTransSt(1);
//			}
//		}
		return transMapper.getTransLogHis(log);
	}

	@Override
	public PageInfo<TransLog> getTransLogPage(int startNum, int pageSize, TransLog log) {
		List<TransLog> ssList = null;
		String tableNum = positOprStatisticsMapper.getCurLogNum();
		log.setTableNum(tableNum);
		PageHelper.startPage(startNum, pageSize);
		if("his".equals(log.getQueryType())){
			ssList = getTransLogHis(log);
		}else{
			ssList = getTransLogCur(log);
		}
		PageInfo<TransLog> ssPage = new PageInfo<TransLog>(ssList);
		ssPage.getList().stream().filter(e -> {
			e.setSettleDate(LocalDate.parse(e.getSettleDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
//			if(e.getTransId().equals(BaseConstants.TransCode.CW10.getCode())&&e.getTransSt()==3){
//				e.setTransType(BaseConstants.TransCode.CW11.getValue());
//			}else if(e.getTransId().equals(BaseConstants.TransCode.CW71.getCode())&&e.getTransSt()==3){
//				e.setTransType(BaseConstants.TransCode.CW74.getValue());
//			}else{
				e.setTransType(BaseConstants.TransCode.findByCode(e.getTransId()).getValue());
//			}
			e.setTransAmt(NumberUtils.RMBCentToYuan(e.getTransAmt()));
			e.setRespCode(BaseConstants.ITFRespCode.findByCode(e.getRespCode()).getValue());
			e.setTransStat(BaseConstants.TransType.findByCode(String.valueOf(e.getTransSt())).getValue());
			return true;
			}).collect(Collectors.toList());
		return ssPage;
	}

	@Override
	public TransLog getTransLogByDmsRelatedKey(TransLog transLog) {
		TransLog log = transMapper.getTransLogByDmsRelatedKey(transLog);
		log.setSettleDate(LocalDate.parse(log.getSettleDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
		log.setTransAmt(NumberUtils.RMBCentToYuan(log.getTransAmt()));
		return log;
	}

}
