package com.cn.thinkx.wecard.centre.module.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeys;
import com.cn.thinkx.wecard.centre.module.biz.mapper.CardKeysMapper;
import com.cn.thinkx.wecard.centre.module.biz.service.CardKeysService;

@Service("cardKeysService")
public class CardKeysServiceImpl implements CardKeysService {

	@Autowired
	private CardKeysMapper cardKeysMapper;

	@Override
	public List<CardKeys> getCardKeysList(CardKeys cardKeys) {
		return this.cardKeysMapper.getCardKeysList(cardKeys);
	}

	@Override
	public int insertCardKeys(CardKeys cardKeys) {
		return this.cardKeysMapper.insertCardKeys(cardKeys);
	}

	@Override
	public int updateCardKeys(CardKeys cardKeys) {
		return cardKeysMapper.updateCardKeys(cardKeys);
	}

	@Override
	public String getCardKeysCount(String accountId) {
		return this.cardKeysMapper.getCardKeysCount(accountId);
	}

	@Override
	public List<CardKeys> getCardKeysByAccountId(String accountId) {
		return this.cardKeysMapper.getCardKeysByAccountId(accountId);
	}

	@Override
	public List<CardKeys> getCardKeysByDataStat() {
		return this.cardKeysMapper.getCardKeysByDataStat();
	}

}
