package com.kingston.mmorpg.net.socket.codec.impl.reflect;

import java.nio.ByteBuffer;

public class IntCodec extends Codec {


	@Override
	public Integer decode(ByteBuffer in, Class<?> type, Class<?> wrapper) {
		return ArrayCodec.ByteBuffUtil.readInt(in);
	}

	@Override
	public void encode(ByteBuffer out, Object value, Class<?> wrapper) {
		ArrayCodec.ByteBuffUtil.writeInt(out, (int)value);
	}
}
