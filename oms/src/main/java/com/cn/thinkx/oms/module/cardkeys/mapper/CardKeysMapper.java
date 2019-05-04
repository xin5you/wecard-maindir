package com.cn.thinkx.oms.module.cardkeys.mapper;

import com.cn.thinkx.oms.module.cardkeys.model.CardKeys;

public interface CardKeysMapper {
	
	CardKeys getCardKeysByCardKey(CardKeys card);
	
	int updateCardKeys(CardKeys card);
}
