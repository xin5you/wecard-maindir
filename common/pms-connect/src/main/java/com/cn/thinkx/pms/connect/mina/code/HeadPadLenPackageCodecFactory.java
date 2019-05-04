package com.cn.thinkx.pms.connect.mina.code;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class HeadPadLenPackageCodecFactory implements ProtocolCodecFactory {
	private final HeadPadLenPackageEncoder encoder;
	private final HeadPadLenPackageDecoder decoder;

	public HeadPadLenPackageCodecFactory() {
		encoder = new HeadPadLenPackageEncoder();
		decoder = new HeadPadLenPackageDecoder();

	}

	public HeadPadLenPackageCodecFactory(int lenFieldLen, String lenFieldType, boolean includeLenField) {
		this();
		encoder.setLenFieldType(lenFieldType);
		encoder.setLenFieldLen(lenFieldLen);
		encoder.setIncludeLenField(includeLenField);

		decoder.setLenFieldType(lenFieldType);
		decoder.setLenFieldLen(lenFieldLen);
		decoder.setIncludeLenField(includeLenField);
	}

	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {

		return decoder;
	}

	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {

		return encoder;
	}

}
