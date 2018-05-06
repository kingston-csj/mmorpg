package com.kingston.mmorpg.framework.net.socket.serializer;

import io.netty.buffer.ByteBuf;

public class ShortSerializer extends Serializer {

	@Override
	public Short decode(ByteBuf in, Class<?> type, Class<?> wrapper) {
		return in.readShort();
	}

	@Override
	public void encode(ByteBuf out, Object value, Class<?> wrapper) {
		out.writeShort((short)value);
	}

}
