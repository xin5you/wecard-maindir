package com.cn.thinkx.oms.module.phoneRecharge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.phoneRecharge.service.ChannelProductService;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelItemList;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelProductInf;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelItemListFacade;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelProductInfFacade;

@Service("channelProductService")
public class ChannelProductServiceImpl implements ChannelProductService{

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TelChannelProductInfFacade telChannelProductInfFacade;

	@Autowired
	private TelChannelItemListFacade telChannelItemListFacade;

	@Override
	public int deleteTelChannelProductInf(String productId) {
		try {
			telChannelProductInfFacade.deleteTelChannelProductInfById(productId);
			telChannelItemListFacade.deleteByProductId(productId);
		} catch (Exception e) {
			logger.error("## 删除分销商产品信息异常", e);
		}
		return 0;
	}

	
}
