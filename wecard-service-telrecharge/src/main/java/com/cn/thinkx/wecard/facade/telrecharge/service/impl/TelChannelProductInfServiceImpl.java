package com.cn.thinkx.wecard.facade.telrecharge.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.facade.telrecharge.mapper.TelChannelProductInfMapper;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelProductInf;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelProductInfFacade;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("telChannelProductInfFacade")
public class TelChannelProductInfServiceImpl implements TelChannelProductInfFacade {

	@Autowired
	private TelChannelProductInfMapper telChannelProductInfMapper;

	@Override
	public TelChannelProductInf getTelChannelProductInfById(String productId) throws Exception {
		return telChannelProductInfMapper.getById(productId);
	}

	@Override
	public int saveTelChannelProductInf(TelChannelProductInf telChannelProductInf) throws Exception {
		int oper = telChannelProductInfMapper.insert(telChannelProductInf);
		return oper;
	}

	/**
	 * 保存对象返回ID
	 * 
	 * @param telChannelProductInf
	 * @return
	 * @throws Exception
	 */
	public String saveTelChannelProductForId(TelChannelProductInf telChannelProductInf) throws Exception {
		int oper = telChannelProductInfMapper.insert(telChannelProductInf);
		if (oper > 0) {
			return telChannelProductInf.getProductId();
		} else {
			return "";
		}
	}

	@Override
	public int updateTelChannelProductInf(TelChannelProductInf telChannelProductInf) throws Exception {
		return telChannelProductInfMapper.update(telChannelProductInf);
	}

	@Override
	public int deleteTelChannelProductInfById(String productId) throws Exception {
		return telChannelProductInfMapper.deleteById(productId);
	}

	/**
	 * 获取分销商产品的折扣
	 * 
	 * @return maps -->productId:产品编号, operId:运营商，productType: 类型，
	 *         areaName:地区名称，productAmt:产品面额（3位小数）
	 */
	public TelChannelProductInf getProductRateByMaps(Map maps) {
		return telChannelProductInfMapper.getProductRateByMaps(maps);
	}

	@Override
	public List<TelChannelProductInf> getTelChannelProductInfList(TelChannelProductInf telChannelProductInf)
			throws Exception {
		return telChannelProductInfMapper.getList(telChannelProductInf);
	}

	@Override
	public PageInfo<TelChannelProductInf> getTelChannelProductInfPage(int startNum, int pageSize,
			TelChannelProductInf telChannelProductInf) throws Exception {
		PageHelper.startPage(startNum, pageSize);
		List<TelChannelProductInf> telChannelProductInfList = getTelChannelProductInfList(telChannelProductInf);
		for (TelChannelProductInf telChannelProductInf2 : telChannelProductInfList) {
			if (!StringUtil.isNullOrEmpty(telChannelProductInf2.getOperId()))
				telChannelProductInf2
						.setOperId(BaseConstants.OperatorType.findByCode(telChannelProductInf2.getOperId()));
			if (!StringUtil.isNullOrEmpty(telChannelProductInf2.getProductType()))
				telChannelProductInf2.setProductType(
						BaseConstants.ChannelProductProType.findByCode(telChannelProductInf2.getProductType()));
			if (!StringUtil.isNullOrEmpty(telChannelProductInf2.getAreaFlag()))
				telChannelProductInf2.setAreaFlag(
						BaseConstants.ChannelProductAreaFlag.findByCode(telChannelProductInf2.getAreaFlag()));
		}
		PageInfo<TelChannelProductInf> telProviderInfPage = new PageInfo<TelChannelProductInf>(
				telChannelProductInfList);
		return telProviderInfPage;
	}

	@Override
	public List<TelChannelProductInf> getChannelProductListByChannelId(String channelId) throws Exception {
		return telChannelProductInfMapper.getChannelProductListByChannelId(channelId);
	}

	@Override
	public TelChannelProductInf getChannelProductByItemId(String id) throws Exception {
		return telChannelProductInfMapper.getChannelProductByItemId(id);
	}
}
