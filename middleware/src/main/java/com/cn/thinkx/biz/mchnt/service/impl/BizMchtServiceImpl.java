package com.cn.thinkx.biz.mchnt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.biz.core.mapper.CtrlSystemMapper;
import com.cn.thinkx.biz.core.model.ImageManager;
import com.cn.thinkx.biz.core.service.ImageManagerService;
import com.cn.thinkx.biz.mchnt.mapper.BizMchtMapper;
import com.cn.thinkx.biz.mchnt.model.CardTransInf;
import com.cn.thinkx.biz.mchnt.model.MchtCommodities;
import com.cn.thinkx.biz.mchnt.model.MerchantInf;
import com.cn.thinkx.biz.mchnt.model.ShopDetailInf;
import com.cn.thinkx.biz.mchnt.model.ShopInf;
import com.cn.thinkx.biz.mchnt.model.ShopListInf;
import com.cn.thinkx.biz.mchnt.service.BizMchtService;
import com.cn.thinkx.biz.user.model.UserMerchantAcct;
import com.cn.thinkx.common.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.AppType;
import com.cn.thinkx.pms.base.utils.BaseConstants.Application;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("bizMchtService")
public class BizMchtServiceImpl implements BizMchtService {
	
	@Autowired
	@Qualifier("bizMchtMapper")
	private BizMchtMapper bizMchtMapper;
	
	@Autowired
	@Qualifier("ctrlSystemMapper")
	private CtrlSystemMapper ctrlSystemMapper;
	
	@Autowired
	@Qualifier("imageManagerService")
	private ImageManagerService imageManagerService;

	@Override
	public MerchantInf getMerchantInfByCode(String mchntCode) {
		return bizMchtMapper.getMerchantInfByCode(mchntCode);
	}

	@Override
	public String getProCodeByMchntCode(String mchntCode) {
		return bizMchtMapper.getProCodeByMchntCode(mchntCode);
	}

	@Override
	public List<MchtCommodities> getSellingCardList(String mchtCode) {
		List<MchtCommodities> list = bizMchtMapper.getSellingCardList(mchtCode);
		if (list == null)
			list = new ArrayList<MchtCommodities>();
		return list;
	}

	@Override
	public MchtCommodities getSellingCard(MchtCommodities mc) {
		return bizMchtMapper.getSellingCard(mc);
	}

	@Override
	public ShopDetailInf getShopSimpleInfo(ShopInf shop) {
		return bizMchtMapper.getShopSimpleInfo(shop);
	}

	@Override
	public ShopDetailInf getShopDetailInfo(ShopInf shop) {
		ShopDetailInf detail = bizMchtMapper.getShopDetailInfo(shop);
		if (detail != null) {
			ImageManager im = new ImageManager();
			im.setApplicationId(detail.getShopCode());
			im.setApplication(Application.APP_SHOP.getCode());
			im.setApplicationType(AppType.A2002.getCode());
			List<String> temp = imageManagerService.getImagesUrl(im);
			List<String> urlList = new ArrayList<String>();
			if (temp == null || temp.size() < 1) {
				urlList.add(RedisDictProperties.getInstance().getdictValueByCode("HKB_DEFAULT_SHOP_IMG"));
			} else {
				for (String url : temp) {
					urlList.add(RedisDictProperties.getInstance().getdictValueByCode("HKB_URL_IMG") + url);
				}
			}
			temp = null;
			detail.setShopImages(urlList);
		}
		return detail;
	}

	public List<ShopListInf> getShopListInfo(ShopInf shop) {
		return bizMchtMapper.getShopListInfo(shop);
	}

	/**
	 * 获取商户门店列表(分页查询)
	 * 
	 * @param startNum
	 * @param pageSize
	 * @param shop
	 * @return
	 */
	public PageInfo<ShopListInf> getShopListInfoPage(int startNum, int pageSize, ShopInf shop) {
		PageHelper.startPage(startNum, pageSize);
		List<ShopListInf> shopList = getShopListInfo(shop);
		ImageManager im = new ImageManager();
		for (ShopListInf s : shopList) {
			im.setApplicationId(s.getShopCode());
			im.setApplication(Application.APP_SHOP.getCode());
			im.setApplicationType(AppType.A2001.getCode());
			List<String> urlList = imageManagerService.getImagesUrl(im);
			if (urlList != null && urlList.size() > 0) {
				s.setBrandLogo(urlList.get(0));// 商户LOGO目前只有一张
			} else {
				s.setBrandLogo(null);
			}
		}
		PageInfo<ShopListInf> shopPage = new PageInfo<ShopListInf>(shopList);
		return shopPage;
	}

	/**
	 * 获取会员卡交易明细(分页查询)
	 * 
	 * @param startNum
	 * @param pageSize
	 * @param acc
	 * @return
	 */
	public PageInfo<CardTransInf> getCardTransDetailListPage(int startNum, int pageSize, UserMerchantAcct acc) {
		PageHelper.startPage(startNum, pageSize);
		List<CardTransInf> transList = bizMchtMapper.getCardTransDetailList(acc);
		if (transList != null && transList.size() > 0) {
			for (CardTransInf ct : transList) {
				ct.setTransIdDesc(BaseConstants.TransCode.findByCode(ct.getTransId()).getValue());
			}
		}
		PageInfo<CardTransInf> transListPage = new PageInfo<CardTransInf>(transList);
		return transListPage;
	}
}
