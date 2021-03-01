package com.kingston.mmorpg.net.socket.codec.impl.reflect;

import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class CollectionCodec extends Codec {

    @Override
    public Object decode(ByteBuffer in, Class<?> type, Class<?> wrapper) {
        int size = ArrayCodec.ByteBuffUtil.readShort(in);
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
            Codec fieldCodec = getSerializer(wrapper);
            Object eleValue = fieldCodec.decode(in, wrapper, null);
            result.add(eleValue);
        }

        return result;
    }

    @Override
    public void encode(ByteBuffer out, Object value, Class<?> wrapper) {
        if (value == null) {
            ArrayCodec.ByteBuffUtil.writeShort(out, (short) 0);
            return;
        }
        Collection<Object> collection = (Collection) value;
        int size = collection.size();
        ArrayCodec.ByteBuffUtil.writeShort(out, (short) size);

        for (Object elem : collection) {
            Class<?> clazz = elem.getClass();
            Codec fieldCodec = getSerializer(clazz);
            fieldCodec.encode(out, elem, wrapper);
        }
    }

}
