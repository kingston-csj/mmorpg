package com.kingston.mmorpg.net.message.codec.impl.reflect;

import java.nio.ByteBuffer;

public class StringCodec extends Codec {

	@Override
	public String decode(ByteBuffer in, Class<?> type, Class<?> wrapper) {
		return ArrayCodec.ByteBuffUtil.readUtf8(in);
	}

	@Override
	public void encode(ByteBuffer out, Object value, Class<?> wrapper) {
		ArrayCodec.ByteBuffUtil.writeUtf8(out, (String)value);
	}

}
