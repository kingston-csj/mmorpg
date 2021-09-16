package org.forfun.mmorpg.protocol.codec.impl.reflect;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;

public class ArrayCodec extends Codec {

    @Override
    public Object decode(ByteBuffer in, Class<?> type, Class<?> wrapper) {
        int size = ByteBuffUtil.readShort(in);

        Object array = ReflectUtil.newArray(type, wrapper, size);

        for (int i = 0; i < size; i++) {
            Codec fieldCodec = getSerializer(wrapper);
            Object eleValue = fieldCodec.decode(in, wrapper, null);
            Array.set(array, i, eleValue);
        }

        return array;
    }

    @Override
    public void encode(ByteBuffer out, Object value, Class<?> wrapper) {
        if (value == null) {
            ByteBuffUtil.writeShort(out, (short) 0);
            return;
        }
        int size = Array.getLength(value);
        ByteBuffUtil.writeShort(out, (short) size);
        for (int i = 0; i < size; i++) {
            Object elem = Array.get(value, i);
            Class<?> clazz = elem.getClass();
            Codec fieldCodec = getSerializer(clazz);
            fieldCodec.encode(out, elem, wrapper);
        }
    }

}
