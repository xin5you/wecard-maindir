package com.cn.thinkx.oms.module.cardkeys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.cardkeys.mapper.CardKeysMapper;
import com.cn.thinkx.oms.module.cardkeys.service.CardKeysService;

@Service("cardKeysService")
public class CardKeysServiceImpl implements CardKeysService {

	@Autowired
	private CardKeysMapper cardKeysMapper;


}
