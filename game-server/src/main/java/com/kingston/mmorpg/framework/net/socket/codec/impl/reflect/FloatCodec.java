package com.kingston.mmorpg.framework.net.socket.codec.impl.reflect;

import java.nio.ByteBuffer;

public class FloatCodec extends Codec {


	@Override
	public Float decode(ByteBuffer in, Class<?> type, Class<?> wrapper) {
		return ArrayCodec.ByteBuffUtil.readFloat(in);
	}

	@Override
	public void encode(ByteBuffer out, Object value, Class<?> wrapper) {
		ArrayCodec.ByteBuffUtil.writeFloat(out, (float)value);
	}
}
