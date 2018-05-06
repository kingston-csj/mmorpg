package com.kingston.mmorpg.framework.net.socket.serializer;

import io.netty.buffer.ByteBuf;

public class IntSerializer extends Serializer {

	@Override
	public Integer decode(ByteBuf in, Class<?> type, Class<?> wrapper) {
		return Integer.valueOf(in.readInt());
	}

	@Override
	public void encode(ByteBuf out, Object value, Class<?> wrapper) {
		out.writeInt((int)value);
	}

}
