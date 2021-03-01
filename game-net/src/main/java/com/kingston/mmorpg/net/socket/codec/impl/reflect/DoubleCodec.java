package com.kingston.mmorpg.net.socket.codec.impl.reflect;

import java.nio.ByteBuffer;

public class DoubleCodec extends Codec {

	@Override
	public Double decode(ByteBuffer in, Class<?> type, Class<?> wrapper) {
		return ArrayCodec.ByteBuffUtil.readDouble(in);
	}

	@Override
	public void encode(ByteBuffer out, Object value, Class<?> wrapper) {
		ArrayCodec.ByteBuffUtil.writeDouble(out, (double)value);
	}
}
