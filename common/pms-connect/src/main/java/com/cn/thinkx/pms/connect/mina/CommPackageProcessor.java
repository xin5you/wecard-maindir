package com.cn.thinkx.pms.connect.mina;

import org.apache.mina.core.buffer.IoBuffer;

import com.cn.thinkx.pms.connect.entity.CommMessage;

public interface CommPackageProcessor {

	CommMessage commDecode(IoBuffer in) throws Exception;

	IoBuffer commEncode(CommMessage message) throws Exception;
}
