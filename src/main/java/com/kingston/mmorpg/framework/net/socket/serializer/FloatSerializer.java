package com.kingston.mmorpg.framework.net.socket.serializer;

import io.netty.buffer.ByteBuf;

public class FloatSerializer extends Serializer {

	@Override
	public Float decode(ByteBuf in, Class<?> type, Class<?> wrapper) {
		return in.readFloat();
	}

	@Override
	public void encode(ByteBuf out, Object value, Class<?> wrapper) {
		out.writeFloat((float)value);
	}

}
