package com.kingston.mmorpg.framework.net.socket.serializer;

import io.netty.buffer.ByteBuf;

public class StringSerializer extends Serializer {

	@Override
	public String decode(ByteBuf in, Class<?> type, Class<?> wrapper) {
		return ByteBuffUtils.readUtf8(in);
	}

	@Override
	public void encode(ByteBuf out, Object value, Class<?> wrapper) {
		ByteBuffUtils.writeUtf8(out, (String)value);
	}

}
