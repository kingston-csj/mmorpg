package com.kingston.mmorpg.framework.net.socket.serializer;

import java.lang.reflect.Array;

import io.netty.buffer.ByteBuf;

public class ArraySerializer extends Serializer {

	@Override
	public Object decode(ByteBuf in, Class<?> type) {
		int size = in.readShort();
		Class<?> wrapper = type.getComponentType();
		Object array = Array.newInstance(wrapper, size);

		for (int i = 0; i < size; i++) {
			Object eleValue = Serializer.readClassAndObject(in);
			Array.set(array, i, eleValue);
		}

		return array;
	}

	@Override
	public void encode(ByteBuf out, Object value) {
		if (value == null) {
			out.writeShort((short) 0);
			return;
		}
		int size = Array.getLength(value);
		out.writeShort((short) size);
		Class<?> wrapper = value.getClass().getComponentType();
		for (int i = 0; i < size; i++) {
			Object elem = Array.get(value, i);
			Class<?> clazz = elem.getClass();
			Serializer.writeClassAndObject(out, elem);
		}
	}

}
