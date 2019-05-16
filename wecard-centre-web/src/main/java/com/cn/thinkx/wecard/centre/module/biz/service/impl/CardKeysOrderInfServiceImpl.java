package com.cn.thinkx.wecard.centre.module.biz.service.impl;

import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysOrderInf;
import com.cn.thinkx.wecard.centre.module.biz.mapper.CardKeysOrderInfMapper;
import com.cn.thinkx.wecard.centre.module.biz.service.CardKeysOrderInfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service("cardKeysOrderInfService")
public class CardKeysOrderInfServiceImpl implements CardKeysOrderInfService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CardKeysOrderInfMapper cardKeysOrderInfMapper;

    @Override
    public Set<CardKeysOrderInf> getCardKeysOrderInfList(CardKeysOrderInf cko) {
        List<CardKeysOrderInf> list = cardKeysOrderInfMapper.getCardKeysOrderInfList(cko);
        Iterator<CardKeysOrderInf> it = list.iterator();
        while (it.hasNext()) {
            CardKeysOrderInf obj = it.next();
            // 根据乐观锁防止重复代付
            if (cardKeysOrderInfMapper.updateCkoByLockversion(obj) < 1) {
                it.remove();
                logger.info("★★★★★卡密订单{}重复代付操作，已从集合删除★★★★★", obj.toString());
            }
        }
        // 去重
        return new HashSet<>(list);
    }

    @Override
    public int insertCardKeysOrderInf(CardKeysOrderInf cardKeysOrderInf) {
        return this.cardKeysOrderInfMapper.insertCardKeysOrderInf(cardKeysOrderInf);
    }

    @Override
    public int updateCardKeysOrderInf(CardKeysOrderInf cardKeysOrderInf) {
        return this.cardKeysOrderInfMapper.updateCardKeysOrderInf(cardKeysOrderInf);
    }

    @Override
    public int updateCkoByLockversion(CardKeysOrderInf cardKeysOrderInf) {
        return cardKeysOrderInfMapper.updateCkoByLockversion(cardKeysOrderInf);
    }

    @Override
    public CardKeysOrderInf getOrderNumByOrderId(CardKeysOrderInf cko) {
        return this.cardKeysOrderInfMapper.getOrderNumByOrderId(cko);
    }

}
