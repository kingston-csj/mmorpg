package com.kingston.mmorpg.framework.net.socket.serializer;

import java.lang.reflect.Array;

import io.netty.buffer.ByteBuf;

public class ArraySerializer extends Serializer {

	@Override
	public Object decode(ByteBuf in, Class<?> type, Class<?> wrapper) {
		int size = in.readShort();
		
		Object array = newArray(type, wrapper, size);
		
		for (int i=0;i<size; i++) {
			Serializer fieldCodec = Serializer.getSerializer(wrapper);
			Object eleValue = fieldCodec.decode(in, wrapper, null);
			Array.set(array, i, eleValue);
		}

		return array;
	}
	
	private static Object newArray(Class<?> clazz, Class<?> wrapper, int size) {
		String name = clazz.getName();
		switch (name) {
		case "[B":
			return new byte[size];
		default:
			return Array.newInstance(wrapper, size);
		}
	}

	@Override
	public void encode(ByteBuf out, Object value, Class<?> wrapper) {
		if (value == null) {
			out.writeShort((short)0);
			return;
		}
		int size = Array.getLength(value);
		out.writeShort((short)size);
		for (int i=0; i<size; i++) {
			Object elem = Array.get(value, i);
			Class<?> clazz = elem.getClass();
			Serializer fieldCodec = Serializer.getSerializer(clazz);
			fieldCodec.encode(out, elem, wrapper);
		}
	}

}
