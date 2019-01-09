package com.kingston.mmorpg.framework.net.socket.serializer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.buffer.ByteBuf;

/**
 * 消息或vo的解析器
 * 
 * @author kingston
 */
public class MessageSerializer extends Serializer {

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
			Serializer serializer = Serializer.getSerializer(type);

			fieldsMeta.add(FieldCodecMeta.valueOf(field, serializer));
		}

		class2FieldsMetas.put(clazz, fieldsMeta);
	}

	@Override
	public Object decode(ByteBuf in, Class<?> type) {
		try {
			List<FieldCodecMeta> fieldsMeta = class2FieldsMetas.get(type);
			Object bean = type.newInstance();
			for (FieldCodecMeta fieldMeta : fieldsMeta) {
				Field field = fieldMeta.getField();
				Serializer fieldCodec = fieldMeta.getSerializer();
				Object value = fieldCodec.decode(in, fieldMeta.getType());
				field.set(bean, value);

			}
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void encode(ByteBuf out, Object message) {
		try {
			List<FieldCodecMeta> fieldsMeta = class2FieldsMetas.get(message.getClass());
			for (FieldCodecMeta fieldMeta : fieldsMeta) {
				Field field = fieldMeta.getField();
				Serializer fieldCodec = fieldMeta.getSerializer();
				Object value = field.get(message);
				fieldCodec.encode(out, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
