package com.cn.thinkx.pms.connect.mina.processor;

import java.io.UnsupportedEncodingException;

import org.apache.mina.core.buffer.IoBuffer;

import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pms.connect.entity.CommMessage;
import com.cn.thinkx.pms.connect.mina.CommPackageProcessor;
import com.cn.thinkx.pms.connect.utils.ProtocolCodecConst;

public class HeadLenCommPackProcessorImpl implements CommPackageProcessor {

	private String lenFieldType = ProtocolCodecConst.lenTypeNumStr;
	private int lenFieldLen = ProtocolCodecConst.lenFieldLen;
	private boolean includeLenField = ProtocolCodecConst.includeLenField;

	public CommMessage commDecode(IoBuffer in) throws Exception {

		int x = in.remaining();
		if (x > lenFieldLen) {
			byte[] lenBuffer = new byte[lenFieldLen];
			in.get(lenBuffer, 0, lenFieldLen);
			int length = getPackLenFromIoBuffer(lenBuffer);
			if (includeLenField) {
				length = length - lenFieldLen;
			}
			if (x >= length + lenFieldLen) {
				byte[] body = new byte[length];
				byte[] message = new byte[length + lenFieldLen];

				System.arraycopy(lenBuffer, 0, message, 0, lenFieldLen);

				in.get(body, 0, length);
				System.arraycopy(body, 0, message, lenFieldLen, length);
				//FileUtils.writeByteArrayToFile(new File("c:/2.txt"), body);
				CommMessage inmessage = new CommMessage();
				inmessage.setLength(length);
				inmessage.setMessagbody(body);
				inmessage.setOriginMessag(message);
				return inmessage;
			}
		}
		return null;
	}

	public IoBuffer commEncode(CommMessage message) throws Exception {
		IoBuffer buffer = IoBuffer.allocate(1024);
		buffer.setAutoExpand(true);

		int bytesize = message.getMessagbody().length;
		byte[] fullPackage = new byte[bytesize + lenFieldLen];

		byte[] lenBuffer = getPackLenBytes(bytesize);
		System.arraycopy(lenBuffer, 0, fullPackage, 0, lenFieldLen);
		System.arraycopy(message.getMessagbody(), 0, fullPackage, lenFieldLen, bytesize);
		message.setOriginMessag(fullPackage);

		buffer.put(message.getOriginMessag());
		return buffer;
	}

	private int getPackLenFromIoBuffer(byte[] lenBuffer) throws Exception {
		int length = -1;
		if (lenFieldType.equals(ProtocolCodecConst.lenTypeNumStr)) {
			String Slength;
			try {

				Slength = new String(lenBuffer, "us-ascii");
				if (!StringUtil.isEmpty(Slength.trim())) {
					length = Integer.valueOf(Slength);
				} else {
					length = 0;
				}
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
				throw e;
			}

			return length;
		} else if (lenFieldType.equals(ProtocolCodecConst.lenTypeBCD)
				|| lenFieldType.equals(ProtocolCodecConst.lenTypeBinary)) {
			byte lenByte = 0;
			length = 0;
			for (int i = 0; i < lenFieldLen; i++) {
				if (lenFieldType.equals(ProtocolCodecConst.lenTypeBinary)) {
					length = length * 256;
				} else {
					length = length * 100;
				}

				lenByte = lenBuffer[i];
				if (lenFieldType.equals(ProtocolCodecConst.lenTypeBCD)) {
					lenByte = (byte) (((lenByte & 0xF0) / 16) * 10 + (lenByte & 0x0F));
				}
				length = length + lenByte;
			}
		}
		return length;
	}

	private byte[] getPackLenBytes(int messageSize) throws Exception {

		byte[] lenBuffer = new byte[lenFieldLen];
		if (messageSize <= 0) {
			throw new Exception("message length is not correct, which is less than zero");
		}

		int maxSize = 0;

		if (lenFieldType.equals(ProtocolCodecConst.lenTypeNumStr)) {
			maxSize = (int) Math.pow(10, lenFieldLen);
			if (messageSize > maxSize) {
				throw new Exception("message length is too long, which is more than " + maxSize);
			}

			String formatStr = String.format("%%0%dd", lenFieldLen);
			String lenStr = String.format(formatStr, messageSize);

			lenBuffer = lenStr.getBytes("us-ascii");
			// buffer.putString(lenStr, encoder);
		} else if (lenFieldType.equals(ProtocolCodecConst.lenTypeBinary)) {
			byte[] body = lenBuffer;
			byte byteLen = 0;
			int sizeItr = messageSize;
			for (int i = 0; i < lenFieldLen; i++) {
				byteLen = (byte) (sizeItr % 256);
				body[lenFieldLen - i - 1] = byteLen;
				sizeItr = sizeItr / 256;
			}
			// buffer.put(body);
		} else if (lenFieldType.equals(ProtocolCodecConst.lenTypeBCD)) {
			byte[] body = lenBuffer;
			byte byteLenLow = 0;
			byte byteLenHigh = 0;
			byte byteLen = 0;
			int sizeItr = messageSize;
			for (int i = 0; i < lenFieldLen; i++) {
				byteLenLow = (byte) (sizeItr % 10);
				sizeItr = sizeItr / 10;
				byteLenHigh = (byte) (sizeItr % 10);
				sizeItr = sizeItr / 10;

				byteLen = (byte) (byteLenHigh * 16 + byteLenLow);

				body[lenFieldLen - i - 1] = byteLen;
			}
			// buffer.put(body);
		}

		return lenBuffer;
	}

	public void setLenFieldType(String lenFieldType) {
		this.lenFieldType = lenFieldType;
	}

	public void setLenFieldLen(int lenFieldLen) {
		this.lenFieldLen = lenFieldLen;
	}

	public void setIncludeLenField(boolean includeLenField) {
		this.includeLenField = includeLenField;
	}


}
