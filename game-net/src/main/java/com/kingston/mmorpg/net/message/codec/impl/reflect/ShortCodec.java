package com.kingston.mmorpg.net.message.codec.impl.reflect;

import java.nio.ByteBuffer;

public class ShortCodec extends Codec {

	@Override
	public Short decode(ByteBuffer in, Class<?> type, Class<?> wrapper) {
		return ArrayCodec.ByteBuffUtil.readShort(in);
	}

	@Override
	public void encode(ByteBuffer out, Object value, Class<?> wrapper) {
		ArrayCodec.ByteBuffUtil.writeShort(out, (short)value);
	}
}
