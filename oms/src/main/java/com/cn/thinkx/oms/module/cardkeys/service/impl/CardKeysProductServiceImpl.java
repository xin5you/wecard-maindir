package com.cn.thinkx.oms.module.cardkeys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.constants.Constants;
import com.cn.thinkx.oms.module.cardkeys.mapper.CardKeysProductMapper;
import com.cn.thinkx.oms.module.cardkeys.model.CardKeysProduct;
import com.cn.thinkx.oms.module.cardkeys.service.CardKeysProductService;
import com.cn.thinkx.oms.util.NumberUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("cardKeysProductService")
public class CardKeysProductServiceImpl implements CardKeysProductService {

	@Autowired
	@Qualifier("cardKeysProductMapper")
	private CardKeysProductMapper cardKeysProductMapper;
	
	@Override
	public String getPrimaryKey() {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", "");
		cardKeysProductMapper.getPrimaryKey(paramMap);
		String id = (String) paramMap.get("id");
		return id;
	}

	@Override
	public CardKeysProduct getCardKeysProductById(String productCode) {
		return this.cardKeysProductMapper.getCardKeysProductById(productCode);
	}
	
	@Override
	public PageInfo<CardKeysProduct> getCardKeysProductPage(int startNum, int pageSize, CardKeysProduct entity) {
		PageHelper.startPage(startNum, pageSize);
		List<CardKeysProduct> list = getCardKeysProductList(entity);
		if (list != null && list.size() > 0) {
			for (CardKeysProduct c : list) {
				if (!StringUtil.isNullOrEmpty(c.getOrgAmount()))
					c.setOrgAmount(String.valueOf(NumberUtils.RMBCentToYuan(c.getOrgAmount())));
				
				if (!StringUtil.isNullOrEmpty(c.getAmount()))
					c.setAmount(String.valueOf(NumberUtils.RMBCentToYuan(c.getAmount())));
				
				if (Constants.isPutaway.P0.getCode().equals(c.getIsPutaway()))
					c.setIsPutaway("已"+Constants.isPutaway.P0.getValue());
				else
					c.setIsPutaway("已"+Constants.isPutaway.P1.getValue());
				
				c.setProductType(BaseConstants.CardProductType.findByCode(c.getProductType()).getValue());
			}
		}
		PageInfo<CardKeysProduct> page = new PageInfo<CardKeysProduct>(list);
		return page;
	}

	@Override
	public List<CardKeysProduct> getCardKeysProductList(CardKeysProduct cardKeysProduct) {
		return this.cardKeysProductMapper.getCardKeysProductList(cardKeysProduct);
	}

	@Override
	public int insertCardKeysProduct(CardKeysProduct cardKeysProduct) {
		return this.cardKeysProductMapper.insertCardKeysProduct(cardKeysProduct);
	}

	@Override
	public int updateCardKeysProduct(CardKeysProduct cardKeysProduct) {
		return this.cardKeysProductMapper.updateCardKeysProduct(cardKeysProduct);
	}

	@Override
	public int deleteCardKeysProduct(String productCode) {
		return this.cardKeysProductMapper.deleteCardKeysProduct(productCode);
	}

	@Override
	public int updateStandUpAndDown(CardKeysProduct cardKeysProduct) {
		return this.cardKeysProductMapper.updateStandUpAndDown(cardKeysProduct);
	}

}
