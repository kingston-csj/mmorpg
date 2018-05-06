package com.kingston.mmorpg.framework.net.socket.serializer;

import io.netty.buffer.ByteBuf;

public class LongSerializer extends Serializer {

	@Override
	public Long decode(ByteBuf in, Class type, Class wrapper) {
		return Long.valueOf(in.readLong());
	}
	

	@Override
	public void encode(ByteBuf out, Object value, Class wrapper) {
		out.writeLong((long)value);
	}
	

}
