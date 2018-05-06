package com.kingston.mmorpg.framework.net.socket.serializer;

import io.netty.buffer.ByteBuf;

public class ByteSerializer extends Serializer {

	@Override
	public Byte decode(ByteBuf in, Class<?> type, Class<?> wrapper) {
		return in.readByte();
	}

	@Override
	public void encode(ByteBuf out, Object value, Class<?> wrapper) {
		out.writeByte((byte)value);
	}

}
