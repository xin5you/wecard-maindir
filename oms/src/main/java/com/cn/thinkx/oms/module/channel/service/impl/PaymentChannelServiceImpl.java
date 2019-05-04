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
import com.cn.thinkx.oms.module.channel.model.PaymentChannel;
import com.cn.thinkx.oms.module.channel.service.PaymentChannelService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseIntegrationPayConstants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("paymentChannelService")
public class PaymentChannelServiceImpl implements PaymentChannelService{

	@Autowired
	@Qualifier("paymentChannelMapper")
	private PaymentChannelMapper paymentChannelMapper;
	
	@Autowired
	@Qualifier("paymentChannelApiMapper")
	private PaymentChannelApiMapper paymentChannelApiMapper;
	
	@Override
	public int insertPaymentChannel(HttpServletRequest req) {
		PaymentChannel pc = new PaymentChannel();
		pc.setChannelNo(StringUtils.nullToString(req.getParameter("channelNo")));
		pc.setChannelName(StringUtils.nullToString(req.getParameter("channelName")));
		pc.setChannelType(StringUtils.nullToString(req.getParameter("channelType")));
		pc.setRate(StringUtils.nullToString(req.getParameter("rate")));
		pc.setDescription(StringUtils.nullToString(req.getParameter("description")));		
		pc.setEnable(StringUtils.nullToString(req.getParameter("enable")));
		pc.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
		HttpSession session=req.getSession();
		User user=(User)session.getAttribute(Constants.SESSION_USER);
		pc.setCreateUser(user.getId().toString());
		pc.setUpdateUser(user.getId().toString());
		return paymentChannelMapper.insertPaymentChannel(pc);
	}

	@Override
	public int updatePaymentChannelById(HttpServletRequest req) {
		PaymentChannel pc = new PaymentChannel();
		String id = StringUtils.nullToString(req.getParameter("id"));
		pc =getPaymentChannelsById(id);
		pc.setChannelName(StringUtils.nullToString(req.getParameter("channelName")));
		pc.setChannelType(StringUtils.nullToString(req.getParameter("channelType")));
		pc.setRate(StringUtils.nullToString(req.getParameter("rate")));
		pc.setDescription(StringUtils.nullToString(req.getParameter("description")));
		pc.setEnable(StringUtils.nullToString(req.getParameter("enable")));
		HttpSession session=req.getSession();
		User user=(User)session.getAttribute(Constants.SESSION_USER);
		pc.setUpdateUser(user.getId().toString());
		return paymentChannelMapper.updatePaymentChannelById(pc);
	}

	@Override
	public int deletePaymentChannelById(String id) {
		paymentChannelApiMapper.deletePaymentChannelApiByChannelId(id);
		return paymentChannelMapper.deletePaymentChannelById(id);
	}

	@Override
	public List<PaymentChannel> getPaymentChannelsList(PaymentChannel entity) {
		return paymentChannelMapper.getPaymentChannelsList(entity);
	}

	@Override
	public PageInfo<PaymentChannel> getPaymentChannelsListPage(int startNum, int pageSize, PaymentChannel entity) {
		PageHelper.startPage(startNum, pageSize);
		List<PaymentChannel> list = getPaymentChannelsList(entity);
		for (PaymentChannel paymentChannel : list) {
			paymentChannel.setChannelType(BaseIntegrationPayConstants.OMSChannelType.findOMSChannelTypeByCode(paymentChannel.getChannelType()));
		}
		PageInfo<PaymentChannel> page = new PageInfo<PaymentChannel>(list);
		return page;
	}

	@Override
	public PaymentChannel getPaymentChannelsById(String id) {
		return paymentChannelMapper.getPaymentChannelsById(id);
	}

	@Override
	public int getPaymentChannelsByChannelNo(String channelNo) {
		return paymentChannelMapper.getPaymentChannelsByChannelNo(channelNo);
	}
	
}
