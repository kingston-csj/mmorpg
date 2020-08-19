package com.kingston.mmorpg.framework.net.socket.serializer;

import com.kingston.mmorpg.framework.net.socket.codec.ByteBuffUtil;

import java.nio.ByteBuffer;

public class ShortSerializer extends Serializer {

	@Override
	public Short decode(ByteBuffer in, Class<?> type, Class<?> wrapper) {
		return ByteBuffUtil.readShort(in);
	}

	@Override
	public void encode(ByteBuffer out, Object value, Class<?> wrapper) {
		ByteBuffUtil.writeShort(out, (short)value);
	}
}
