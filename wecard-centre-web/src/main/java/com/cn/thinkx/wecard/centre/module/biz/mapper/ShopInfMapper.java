package com.cn.thinkx.wecard.centre.module.biz.mapper;

import com.cn.thinkx.pms.base.redis.vo.ShopInfVO;

import java.util.List;

public interface ShopInfMapper {

    /*
     * 查询门店信息以及对应的商户和机构名称
     */
    List<ShopInfVO> getShopInfList();
}
