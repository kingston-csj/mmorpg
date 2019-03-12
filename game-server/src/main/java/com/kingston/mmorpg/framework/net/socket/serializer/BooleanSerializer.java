package com.kingston.mmorpg.framework.net.socket.serializer;

import io.netty.buffer.ByteBuf;

public class BooleanSerializer extends Serializer {

	@Override
	public Boolean decode(ByteBuf in, Class<?> type) {
		return in.readBoolean();
	}

	@Override
	public void encode(ByteBuf out, Object value) {
		out.writeBoolean((boolean) value);
	}

}
