package com.kingston.mmorpg.framework.net.socket.serializer;

import io.netty.buffer.ByteBuf;

public class DoubleSerializer extends Serializer {

	@Override
	public Double decode(ByteBuf in, Class<?> type, Class<?> wrapper) {
		return in.readDouble();
	}

	@Override
	public void encode(ByteBuf out, Object value, Class<?> wrapper) {
		out.writeDouble((double)value);
	}

}
