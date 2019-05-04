package com.cn.thinkx.common.service.module.channel.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cn.thinkx.common.service.module.channel.domain.ChannelSecurityInf;

@Repository
public interface ChannelSecurityInfMapper {

	/**
	 * 查询所有的渠道安全信息
	 * @return list
	 */
	List<ChannelSecurityInf> getAllChannelSecurityInf();
	
	/**
	 * 根据渠道code查询渠道信息
	 * @param channelCode
	 * @return
	 */
	ChannelSecurityInf getChannelSecurityInfById(@Param("channelCode") String channelCode);
	
	/**
	 * 添加渠道信息
	 * @param csi
	 * @return
	 */
	int insertChannelSecurityInf(ChannelSecurityInf csi);
	
	/**
	 * 修改渠道信息
	 * @param csi
	 */
	int updateChannelSecurityInf(ChannelSecurityInf csi);

	/**
	 * 删除渠道信息
	 * @param csi
	 */
	int deleteChannelSecurityInf(@Param("channelCode") String channelCode);
	
	

}