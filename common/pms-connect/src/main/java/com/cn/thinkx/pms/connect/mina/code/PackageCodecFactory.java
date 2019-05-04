package com.cn.thinkx.pms.connect.mina.code;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class PackageCodecFactory implements ProtocolCodecFactory {
  	private final PackageEncoder encoder;
  	private final PackageDecoder decoder;
  

	public PackageCodecFactory(PackageEncoder encoder,PackageDecoder decoder) {
		this.encoder=encoder;
		this.decoder=decoder;
	}

	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {

		return decoder;
	}

	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {

		return encoder;
	}

}
