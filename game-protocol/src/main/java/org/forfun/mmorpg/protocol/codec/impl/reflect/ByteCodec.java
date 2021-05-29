package org.forfun.mmorpg.protocol.codec.impl.reflect;

import java.nio.ByteBuffer;

public class ByteCodec extends Codec {

	@Override
	public Byte decode(ByteBuffer in, Class<?> type, Class<?> wrapper) {
		return ArrayCodec.ByteBuffUtil.readByte(in);
	}

	@Override
	public void encode(ByteBuffer out, Object value, Class<?> wrapper) {
		ArrayCodec.ByteBuffUtil.writeByte(out, (byte)value);
	}

}
