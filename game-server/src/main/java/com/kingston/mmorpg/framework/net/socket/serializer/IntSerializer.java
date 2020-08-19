package com.kingston.mmorpg.framework.net.socket.serializer;

import com.kingston.mmorpg.framework.net.socket.codec.ByteBuffUtil;

import java.nio.ByteBuffer;

public class IntSerializer extends Serializer {


	@Override
	public Integer decode(ByteBuffer in, Class<?> type, Class<?> wrapper) {
		return ByteBuffUtil.readInt(in);
	}

	@Override
	public void encode(ByteBuffer out, Object value, Class<?> wrapper) {
		ByteBuffUtil.writeInt(out, (int)value);
	}
}
