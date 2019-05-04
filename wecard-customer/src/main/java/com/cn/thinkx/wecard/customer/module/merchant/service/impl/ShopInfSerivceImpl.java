package com.cn.thinkx.wecard.customer.module.merchant.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.domain.base.ResultHtml;
import com.cn.thinkx.common.wecard.domain.shop.ShopInf;
import com.cn.thinkx.common.wecard.module.shop.mapper.ShopInfDao;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.wecard.customer.core.util.MessageUtil;
import com.cn.thinkx.wecard.customer.module.merchant.service.ShopInfService;

@Service("shopInfService")
public class ShopInfSerivceImpl implements ShopInfService {

	@Autowired
	private ShopInfDao shopInfDao;

	@Override
	public int addShopInf(ShopInf entity) {
		return shopInfDao.insertShopInf(entity);

	}

	@Override
	public int updateShopInf(ShopInf entity) {
		return shopInfDao.updateShopInf(entity);
	}

	@Override
	public int updateShopInfUrl(ShopInf entity) {
		return shopInfDao.updateShopInfUrl(entity);
	}

	@Override
	public ShopInf getShopInfById(String shopInfId) {
		return shopInfDao.getShopInfById(shopInfId);
	}

	@Override
	public List<ShopInf> findShopInfList(ShopInf entity) {
		return shopInfDao.getShopInfList(entity);
	}

	/***
	 * 门店 管理 新增 编辑
	 * 
	 * @param mchntInfId
	 * @param flag
	 *            0:新增 1:编辑
	 * @param shopInf
	 * @return
	 */
	public ResultHtml updateShopInfByMerchantInf(String mchntInfId, String flag, ShopInf shopInf) {
		ResultHtml result = new ResultHtml();
		result.setStatus(false);
		shopInf.setMchntId(mchntInfId); // 设置所属商户

		shopInf.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode()); // 设置
		if ("1".equals(flag)) {
			ShopInf shop = getShopInfById(shopInf.getShopId()); // 编辑时候根据当前ID查询

			if (shop != null && shop.getMchntId().equals(mchntInfId)) { // 比对当前商户ID是否一致
				shop.setShopName(shopInf.getShopName());
				shop.setShopAddr(shopInf.getShopAddr());
				int y = this.updateShopInf(shopInf);
				if (y > 0) {
					result.setStatus(true);
					return result;
				} else {
					result.setMsg(MessageUtil.ERROR_MSSAGE); // 数据库异常
					return result;
				}
			} else {
				result.setMsg(MessageUtil.SHOPINF_MESSAGE_TIPS_NOTFINDSHOP);
				return result;
			}

		} else {
			int y = this.addShopInf(shopInf);
			if (y > 0) {
				result.setStatus(true);
				return result;
			} else {
				result.setStatus(false);
				result.setMsg(MessageUtil.ERROR_MSSAGE); // 数据库异常
				return result;
			}
		}
	}
	
	public ShopInf getShopInfByCode(String shopCode){
		return shopInfDao.getShopInfByCode(shopCode);
	}

}
