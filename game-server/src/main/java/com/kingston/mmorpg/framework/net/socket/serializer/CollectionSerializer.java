package com.kingston.mmorpg.framework.net.socket.serializer;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.netty.buffer.ByteBuf;

public class CollectionSerializer extends Serializer {

	@Override
	public Object decode(ByteBuf in, Class<?> type) {
		int size = in.readShort();
		int modifier = type.getModifiers();
		Collection<Object> result = null;

		if (Modifier.isAbstract(modifier) || Modifier.isInterface(modifier)) {
			if (List.class.isAssignableFrom(type)) {
				result = new ArrayList<>();
			} else if (Set.class.isAssignableFrom(type)) {
				result = new HashSet<>();
			}
		} else {
			try {
				result = (Collection) type.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				result = new ArrayList<>();
			}
		}

		for (int i = 0; i < size; i++) {
			Object eleValue = Serializer.readClassAndObject(in);
			result.add(eleValue);
		}

		return result;
	}

	@Override
	public void encode(ByteBuf in, Object value) {
		if (value == null) {
			in.writeShort(0);
			return;
		}
		Collection<Object> collection = (Collection) value;
		int size = collection.size();
		in.writeShort(size);
		for (Object elem : collection) {
			Class<?> clazz = elem.getClass();
			Serializer fieldCodec = Serializer.getSerializer(clazz);
			Serializer.writeClassAndObject(in, elem);
		}
	}

}
