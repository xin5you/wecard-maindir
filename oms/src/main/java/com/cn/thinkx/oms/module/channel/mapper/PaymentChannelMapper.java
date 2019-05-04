package com.cn.thinkx.oms.module.channel.mapper;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.cn.thinkx.oms.module.channel.model.PaymentChannel;
/**
 * @author kpp
 */
@Repository("paymentChannelMapper")
public interface PaymentChannelMapper {

	/**
	 * 添加支付通道
	 * 
	 * @param entity
	 * @return
	 */
	int insertPaymentChannel(PaymentChannel entity);
	
	/**
	 * 修改支付通道
	 * 
	 * @param id
	 * @return
	 */
	int updatePaymentChannelById(PaymentChannel entity);
	
	/**
	 * 删除支付通道
	 * 
	 * @param id
	 * @return
	 */
	int deletePaymentChannelById(String id);
	
	/**
	 * 查询支付通道信息
	 * 
	 * @param entity
	 * @return
	 */
	List<PaymentChannel> getPaymentChannelsList(PaymentChannel entity);
	
	/**
	 * 通过主键查询支付通道信息
	 * 
	 * @param id
	 * @return
	 */
	PaymentChannel getPaymentChannelsById(String id);
	
	/**
	 * 修改时间
	 * 
	 * @param id
	 * @return
	 */
	int updatePaymentChannelUpdateTime(String id);
	
	/**
	 * 通过通道号查询
	 * 
	 * @param channelNo
	 * @return
	 */
	int getPaymentChannelsByChannelNo(String channelNo);
}
