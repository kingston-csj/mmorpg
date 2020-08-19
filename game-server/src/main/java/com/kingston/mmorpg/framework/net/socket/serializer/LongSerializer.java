package com.kingston.mmorpg.framework.net.socket.serializer;

import com.kingston.mmorpg.framework.net.socket.codec.ByteBuffUtil;

import java.nio.ByteBuffer;

public class LongSerializer extends Serializer {

	@Override
	public Long decode(ByteBuffer in, Class<?> type, Class<?> wrapper) {
		return ByteBuffUtil.readLong(in);
	}

	@Override
	public void encode(ByteBuffer out, Object value, Class<?> wrapper) {
		ByteBuffUtil.writeLong(out, (long)value);
	}
}
