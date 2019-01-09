package com.kingston.mmorpg.framework.net.socket.serializer;

import io.netty.buffer.ByteBuf;

public class ByteSerializer extends Serializer {

	@Override
	public Byte decode(ByteBuf in, Class<?> type) {
		return in.readByte();
	}

	@Override
	public void encode(ByteBuf out, Object value) {
		out.writeByte((byte)value);
	}

}
