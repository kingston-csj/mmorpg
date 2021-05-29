package org.forfun.mmorpg.protocol.codec.impl.reflect;

import java.nio.ByteBuffer;

public class BooleanCodec extends Codec {

	@Override
	public Boolean decode(ByteBuffer in, Class<?> type, Class<?> wrapper) {
		return ArrayCodec.ByteBuffUtil.readBoolean(in);
	}

	@Override
	public void encode(ByteBuffer out, Object value, Class<?> wrapper) {
		ArrayCodec.ByteBuffUtil.writeBoolean(out, (boolean)value);
	}

}
