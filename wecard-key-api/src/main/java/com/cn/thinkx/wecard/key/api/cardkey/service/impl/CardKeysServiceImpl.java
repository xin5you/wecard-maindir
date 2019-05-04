package com.cn.thinkx.wecard.key.api.cardkey.service.impl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.wecard.key.api.cardkey.domain.CardKeys;
import com.cn.thinkx.wecard.key.api.cardkey.mapper.CardKeysMapper;
import com.cn.thinkx.wecard.key.api.cardkey.service.CardKeysService;

@Service("cardKeysService")
public class CardKeysServiceImpl implements CardKeysService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CardKeysMapper cardKeysMapper;

	@Override
	public List<CardKeys> getCardKeysList(CardKeys cardKeys) {
		return this.cardKeysMapper.getCardKeysList(cardKeys);
	}

	@Override
	public int insertCardKeys(Set<CardKeys> cardKeys) {
		try {
			cardKeys.stream().limit(cardKeys.size()).forEach(keys -> {
				cardKeysMapper.insertCardKeys(keys);
			});
		} catch(Exception e) {
			logger.error("## 插入卡密异常", e);
			return 0;
		}
		return cardKeys.size();
	}

	@Override
	public int updateCardKeys(CardKeys cardKeys) {
		return cardKeysMapper.updateCardKeys(cardKeys);
	}

}
