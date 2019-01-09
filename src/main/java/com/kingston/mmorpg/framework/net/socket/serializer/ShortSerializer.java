package com.kingston.mmorpg.framework.net.socket.serializer;

import io.netty.buffer.ByteBuf;

public class ShortSerializer extends Serializer {

	@Override
	public Short decode(ByteBuf in, Class<?> type) {
		return in.readShort();
	}

	@Override
	public void encode(ByteBuf out, Object value) {
		out.writeShort((short)value);
	}

}
