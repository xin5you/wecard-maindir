package com.cn.thinkx.wecard.customer.module.pub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.domain.ecomorder.OrderInf;
import com.cn.thinkx.common.wecard.module.ecomorder.mapper.OrderInfMapper;
import com.cn.thinkx.wecard.customer.module.pub.service.OrderInfService;

@Service("orderInfService")
public class OrderInfServiceImpl implements OrderInfService {

	@Autowired
	private OrderInfMapper orderInfMapper;
	
	@Override
	public OrderInf getOrderInfById(String id) {
		return orderInfMapper.getOrderInfById(id);
	}

}
