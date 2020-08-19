package com.kingston.mmorpg.framework.net.socket.serializer;

import com.kingston.mmorpg.framework.net.socket.codec.ByteBuffUtil;

import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.*;

public class CollectionSerializer extends Serializer {

    @Override
	@SuppressWarnings("unchecked")
    public Object decode(ByteBuffer in, Class<?> type, Class<?> wrapper) {
        int size = ByteBuffUtil.readShort(in);
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
                result = new ArrayList<>();
            }
        }

        for (int i = 0; i < size; i++) {
            Serializer fieldCodec = Serializer.getSerializer(wrapper);
            Object eleValue = fieldCodec.decode(in, wrapper, null);
            result.add(eleValue);
        }

        return result;
    }

    @Override
    public void encode(ByteBuffer out, Object value, Class<?> wrapper) {
        if (value == null) {
            ByteBuffUtil.writeShort(out, (short) 0);
            return;
        }
        Collection<Object> collection = (Collection) value;
        int size = collection.size();
        ByteBuffUtil.writeShort(out, (short) size);

        for (Object elem : collection) {
            Class<?> clazz = elem.getClass();
            Serializer fieldCodec = Serializer.getSerializer(clazz);
            fieldCodec.encode(out, elem, wrapper);
        }
    }

}
