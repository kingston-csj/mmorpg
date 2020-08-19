package com.kingston.mmorpg.framework.net.socket.serializer;

import com.kingston.mmorpg.framework.net.socket.codec.ByteBuffUtil;

import java.nio.ByteBuffer;

public class BooleanSerializer extends Serializer {

	@Override
	public Boolean decode(ByteBuffer in, Class<?> type, Class<?> wrapper) {
		return ByteBuffUtil.readBoolean(in);
	}

	@Override
	public void encode(ByteBuffer out, Object value, Class<?> wrapper) {
		ByteBuffUtil.writeBoolean(out, (boolean)value);
	}

}
