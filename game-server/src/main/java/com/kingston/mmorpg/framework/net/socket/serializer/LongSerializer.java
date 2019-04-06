package com.kingston.mmorpg.framework.net.socket.serializer;

import io.netty.buffer.ByteBuf;

public class LongSerializer extends Serializer {

	@Override
	public Long decode(ByteBuf in, Class<?> type) {
		return Long.valueOf(in.readLong());
	}

	@Override
	public void encode(ByteBuf out, Object value) {
		out.writeLong((long) value);
	}

}
