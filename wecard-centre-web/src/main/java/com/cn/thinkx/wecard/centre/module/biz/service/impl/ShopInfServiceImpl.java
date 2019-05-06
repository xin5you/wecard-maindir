package com.cn.thinkx.wecard.centre.module.biz.service.impl;

import com.cn.thinkx.pms.base.redis.vo.ShopInfVO;
import com.cn.thinkx.wecard.centre.module.biz.mapper.ShopInfMapper;
import com.cn.thinkx.wecard.centre.module.biz.service.ShopInfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("shopInfService")
public class ShopInfServiceImpl implements ShopInfService {

    @Autowired
    private ShopInfMapper shopInfMapper;

    @Override
    public List<ShopInfVO> getShopInfList() {
        return shopInfMapper.getShopInfList();
    }


}
