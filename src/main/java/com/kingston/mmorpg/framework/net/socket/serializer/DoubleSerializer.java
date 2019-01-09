package com.kingston.mmorpg.framework.net.socket.serializer;

import io.netty.buffer.ByteBuf;

public class DoubleSerializer extends Serializer {

	@Override
	public Double decode(ByteBuf in, Class<?> type) {
		return in.readDouble();
	}

	@Override
	public void encode(ByteBuf out, Object value) {
		out.writeDouble((double)value);
	}

}
