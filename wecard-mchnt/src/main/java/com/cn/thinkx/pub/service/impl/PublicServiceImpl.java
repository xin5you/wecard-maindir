package com.cn.thinkx.pub.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.pub.domain.DetailBizInfo;
import com.cn.thinkx.pub.mapper.PublicDao;
import com.cn.thinkx.pub.service.PublicService;
import com.cn.thinkx.service.txn.Java2TxnBusinessFacade;
import com.cn.thinkx.wxclient.service.WxTransLogService;

@Service("publicService")
public class PublicServiceImpl implements PublicService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WxTransLogService wxTransLogService;
	@Autowired
	private Java2TxnBusinessFacade java2TxnBusinessFacade;
	@Autowired
	@Qualifier("publicDao")
	private  PublicDao publicDao;
	
	@Override
	public String getPrimaryKey() {
		Map<String, String> paramMap = new HashMap<String, String>();
    	paramMap.put("id", "");
    	publicDao.getPrimaryKey(paramMap);
		String id = (String) paramMap.get("id");
		return id;
	}

	@Override
	public DetailBizInfo getDetailBizInfo(DetailBizInfo detail) {
		return publicDao.getDetailBizInfo(detail);
	}
}
