package com.kingston.mmorpg.framework.net.socket.codec.impl.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息或vo的解析器
 *
 * @author kingston
 */
public class MessageCodec extends Codec {

    private Map<Class<?>, List<FieldCodecMeta>> class2FieldsMetas = new ConcurrentHashMap<>();

    @Override
    public void onRegister(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        Class<?> currClazz = clazz;

        while (currClazz != Object.class) {
            Collections.addAll(fields, currClazz.getDeclaredFields());
            currClazz = currClazz.getSuperclass();
        }

        LinkedHashMap<Field, Class<?>> sortedFields = new LinkedHashMap<>();
        List<FieldCodecMeta> fieldsMeta = new ArrayList<>();
        for (Field field : fields) {
            int modifier = field.getModifiers();
            if (Modifier.isFinal(modifier) || Modifier.isStatic(modifier) || Modifier.isTransient(modifier)) {
                continue;
            }
            field.setAccessible(true);
            sortedFields.put(field, field.getType());
            Class<?> type = field.getType();
            Codec codec = Codec.getSerializer(type);

            fieldsMeta.add(FieldCodecMeta.valueOf(field, codec));
        }

        class2FieldsMetas.put(clazz, fieldsMeta);
    }

    @Override
    public Object decode(ByteBuffer in, Class<?> type, Class<?> wrapper) {
        try {
            List<FieldCodecMeta> fieldsMeta = class2FieldsMetas.get(type);
            Object bean = type.newInstance();
            for (FieldCodecMeta fieldMeta : fieldsMeta) {
                Field field = fieldMeta.getField();
                Codec fieldCodec = fieldMeta.getCodec();

                Object value = fieldCodec.decode(in, fieldMeta.getType(), fieldMeta.getWrapper());
                field.set(bean, value);

            }
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void encode(ByteBuffer out, Object value, Class<?> wrapper) {
        try {
            List<FieldCodecMeta> fieldsMeta = class2FieldsMetas.get(value.getClass());
            for (FieldCodecMeta fieldMeta : fieldsMeta) {
                Field field = fieldMeta.getField();
                Codec fieldCodec = fieldMeta.getCodec();
                Object fieldVal = field.get(value);
                fieldCodec.encode(out, fieldVal, fieldMeta.getWrapper());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
