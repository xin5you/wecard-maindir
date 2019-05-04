package com.cn.thinkx.merchant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.merchant.domain.CardBal;
import com.cn.thinkx.merchant.mapper.CardBalDao;
import com.cn.thinkx.merchant.service.CardBalService;

@Service("cardBalService")
public class CardBalServiceImpl implements CardBalService {
	
	@Autowired
	@Qualifier("cardBalDao")
	private CardBalDao cardBalDao;

	@Override
	public CardBal getCardBalByInsCodeAndProductCode(CardBal cb) {
		return cardBalDao.getCardBalByInsCodeAndProductCode(cb);
	}

}
