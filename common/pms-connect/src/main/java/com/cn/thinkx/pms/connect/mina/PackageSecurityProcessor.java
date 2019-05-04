package com.cn.thinkx.pms.connect.mina;

import com.cn.thinkx.pms.connect.entity.CommMessage;

/**
 * 检查收发信息的来源
 * 
 * @author sunyue
 * 
 */
public interface PackageSecurityProcessor {
	/**
	 * 验证MAC地址 是否正确
	 * 
	 * @param commMessage
	 * @return
	 * @throws Exception
	 */
	public boolean isValidMac(CommMessage commMessage) throws Exception;

	/**
	 * 编码时生成MAC地址，方便接收时验证
	 * 
	 * @param commMessage
	 * @throws Exception
	 */
	public void generateMac(CommMessage commMessage) throws Exception;

}
