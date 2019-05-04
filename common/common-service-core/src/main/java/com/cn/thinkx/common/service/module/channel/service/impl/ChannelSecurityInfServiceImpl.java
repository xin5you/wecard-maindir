package com.cn.thinkx.common.service.module.channel.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.thinkx.common.service.module.channel.domain.ChannelSecurityInf;
import com.cn.thinkx.common.service.module.channel.mapper.ChannelSecurityInfMapper;
import com.cn.thinkx.common.service.module.channel.service.ChannelSecurityInfService;


@Service("channelSecurityInfService")
@Transactional
public class ChannelSecurityInfServiceImpl implements ChannelSecurityInfService {

	@Autowired
	private ChannelSecurityInfMapper channelSecurityInfMapper;
	
	/**
	 * 查询所有的渠道安全信息
	 * @return list
	 */
	public List<ChannelSecurityInf> getAllChannelSecurityInf(){
		return channelSecurityInfMapper.getAllChannelSecurityInf();
	}
	
	/**
	 * 根据渠道code查询渠道信息
	 * @param channelCode
	 */
	@Override
	public ChannelSecurityInf getChannelSecurityInfById(String channelCode) {
		return channelSecurityInfMapper.getChannelSecurityInfById(channelCode);
	}
	
	/**
	 * 添加渠道信息
	 * @return
	 */
	@Override
	public int insertChannelSecurityInf(ChannelSecurityInf csi) {
		return channelSecurityInfMapper.insertChannelSecurityInf(csi);
	}
	
	/**
	 * 修改渠道信息
	 * @return
	 */
	@Override
	public int updateChannelSecurityInf(ChannelSecurityInf csi) {
		return channelSecurityInfMapper.updateChannelSecurityInf(csi);
	}

	/**
	 * 删除渠道信息
	 * @return
	 */
	@Override
	public int deleteChannelSecurityInf(@Param("channelCode") String channelCode) {
		return channelSecurityInfMapper.deleteChannelSecurityInf(channelCode);
	}

}