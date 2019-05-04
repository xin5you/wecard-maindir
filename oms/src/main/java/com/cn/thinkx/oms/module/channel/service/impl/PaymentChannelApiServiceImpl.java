package com.cn.thinkx.oms.module.channel.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.constants.Constants;
import com.cn.thinkx.oms.module.channel.mapper.PaymentChannelApiMapper;
import com.cn.thinkx.oms.module.channel.mapper.PaymentChannelMapper;
import com.cn.thinkx.oms.module.channel.model.PaymentChannelApi;
import com.cn.thinkx.oms.module.channel.service.PaymentChannelApiService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseIntegrationPayConstants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("paymentChannelApiService")
public class PaymentChannelApiServiceImpl implements PaymentChannelApiService {

	@Autowired
	@Qualifier("paymentChannelApiMapper")
	private PaymentChannelApiMapper paymentChannelApiMapper;
	
	@Autowired
	@Qualifier("paymentChannelMapper")
	private PaymentChannelMapper paymentChannelMapper;

	@Override
	public int insertPaymentChannelApi(HttpServletRequest req) {
		PaymentChannelApi pci = new PaymentChannelApi();
		String channelId = StringUtils.nullToString(req.getParameter("channelId"));
		pci.setChannelId(channelId);
		pci.setName(StringUtils.nullToString(req.getParameter("name")));
		pci.setUrl(StringUtils.nullToString(req.getParameter("url")));
		pci.setApiType(StringUtils.nullToString(req.getParameter("apiType")));
		pci.setDescription(StringUtils.nullToString(req.getParameter("description")));
		pci.setEnable(StringUtils.nullToString(req.getParameter("enable"))); // 1启动 0禁用
		pci.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(Constants.SESSION_USER);
		pci.setCreateUser(user.getId().toString());
		pci.setUpdateUser(user.getId().toString());
		paymentChannelMapper.updatePaymentChannelUpdateTime(channelId);
		return paymentChannelApiMapper.insertPaymentChannelApi(pci);
	}

	@Override
	public int updatePaymentChannelApiById(HttpServletRequest req) {
		PaymentChannelApi pci = new PaymentChannelApi();
		String id = StringUtils.nullToString(req.getParameter("id"));
		String channelId = StringUtils.nullToString(req.getParameter("channelId"));
		pci = getPaymentChannelsApiById(id);
		pci.setChannelId(channelId);
		pci.setName(StringUtils.nullToString(req.getParameter("name")));
		pci.setUrl(StringUtils.nullToString(req.getParameter("url")));
		pci.setApiType(StringUtils.nullToString(req.getParameter("apiType")));
		pci.setDescription(StringUtils.nullToString(req.getParameter("description")));
		pci.setEnable(StringUtils.nullToString(req.getParameter("enable"))); // 1启动 0禁用
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(Constants.SESSION_USER);
		pci.setUpdateUser(user.getId().toString());
		paymentChannelMapper.updatePaymentChannelUpdateTime(channelId);
		return paymentChannelApiMapper.updatePaymentChannelApiById(pci);
	}

	@Override
	public int deletePaymentChannelApiById(String id) {
		PaymentChannelApi paymentChannelApi = paymentChannelApiMapper.getPaymentChannelsApiById(id);
		paymentChannelMapper.updatePaymentChannelUpdateTime(StringUtils.nullToString(paymentChannelApi.getChannelId()));
		return paymentChannelApiMapper.deletePaymentChannelApiById(id);
	}

	@Override
	public List<PaymentChannelApi> getPaymentChannelsApisList(PaymentChannelApi entity) {
		return paymentChannelApiMapper.getPaymentChannelsApisList(entity);
	}

	@Override
	public PaymentChannelApi getPaymentChannelsApiById(String id) {
		PaymentChannelApi paymentChannelApi = paymentChannelApiMapper.getPaymentChannelsApiById(id);
		paymentChannelApi.setApiType(BaseIntegrationPayConstants.TransCode.findByCode(paymentChannelApi.getApiType()).getValue());
		return paymentChannelApi;
	}

	@Override
	public PageInfo<PaymentChannelApi> getPaymentChannelsApiListPage(int startNum, int pageSize,PaymentChannelApi entity) {
		PageHelper.startPage(startNum, pageSize);
		List<PaymentChannelApi> list = getPaymentChannelsApisList(entity);
		for (PaymentChannelApi paymentChannelApi : list) {
			paymentChannelApi.setApiType(BaseIntegrationPayConstants.TransCode.findByCode(paymentChannelApi.getApiType()).getValue());
		}
		PageInfo<PaymentChannelApi> page = new PageInfo<PaymentChannelApi>(list);
		return page;
	}

}
