package com.kingston.mmorpg.framework.net.socket.serializer;

import com.kingston.mmorpg.framework.net.socket.codec.ByteBuffUtil;

import java.nio.ByteBuffer;

public class FloatSerializer extends Serializer {


	@Override
	public Float decode(ByteBuffer in, Class<?> type, Class<?> wrapper) {
		return ByteBuffUtil.readFloat(in);
	}

	@Override
	public void encode(ByteBuffer out, Object value, Class<?> wrapper) {
		ByteBuffUtil.writeFloat(out, (float)value);
	}
}
