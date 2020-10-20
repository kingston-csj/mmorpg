package com.kingston.mmorpg.framework.net.socket.reflect;

import com.kingston.mmorpg.framework.net.socket.MessageFactory;
import com.kingston.mmorpg.framework.net.socket.SerializerMeta;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Codec {

	private static Map<Class<?>, Codec> class2Serializers = new ConcurrentHashMap<>();

	private final static MessageCodec messageSerializer = new MessageCodec();

	public static Map<Integer, SerializerMeta> idMetas = new HashMap<>();

	public static Map<Class<?>, SerializerMeta> classMetas = new HashMap<>();

	private static int nextId = -1;

	static {
		register(Boolean.TYPE, new BooleanCodec());
		register(Boolean.class, new BooleanCodec());
		register(Byte.TYPE, new ByteCodec());
		register(Byte.class, new ByteCodec());
		register(Short.TYPE, new ShortCodec());
		register(Short.class, new ShortCodec());
		register(Integer.TYPE, new IntCodec());
		register(Integer.class, new IntCodec());
		register(Float.TYPE, new FloatCodec());
		register(Float.class, new FloatCodec());
		register(Double.TYPE, new DoubleCodec());
		register(Double.class, new DoubleCodec());
		register(Long.TYPE, new LongCodec());
		register(Long.class, new LongCodec());
		register(String.class, new StringCodec());
		register(List.class, new CollectionCodec());
		register(Set.class, new CollectionCodec());
		register(Object[].class, new ArrayCodec());
	}

	public static void register(Class<?> clazz, Codec codec) {
		int id = nextId();
		registerClassAndId(id, clazz, codec);
	}

	private static synchronized int nextId() {
		return nextId--;
	}

	public static Codec getSerializer(Class<?> clazz) {
		if (class2Serializers.containsKey(clazz)) {
			return class2Serializers.get(clazz);
		}
		return getSerializerMeta(clazz).getCodec();
	}

	public static SerializerMeta getSerializerMeta(Class<?> clazz) {
		if (classMetas.containsKey(clazz)) {
			return classMetas.get(clazz);
		}
		if (clazz.isArray()) {
			return classMetas.get(Object[].class);
		}
		int id = MessageFactory.getInstance().getMessageId(clazz);
		return registerClassAndId(id, clazz, messageSerializer);
	}

	public static SerializerMeta registerClassAndId(int id, Class<?> clazz, Codec codec) {
		SerializerMeta meta = new SerializerMeta(codec, clazz, id);
		idMetas.put(id, meta);
		classMetas.put(clazz, meta);
		codec.onRegister(clazz);
		return meta;
	}

	/**
	 * 消息解码
	 * @param in
	 * @param type
	 * @param wrapper 集合元素包装类
	 * @return
	 */
	public abstract Object decode(ByteBuffer in, Class<?> type, Class<?> wrapper);


	/**
	 * 消息编码
	 * @param out
	 * @param value
	 * @param wrapper 集合元素包装类
	 */
	public abstract void encode(ByteBuffer out, Object value, Class<?> wrapper);

	public void onRegister(Class<?> clazz) {

	}

	public static void registerClass(Class<?> clazz, int id) {
		registerClass(clazz, id, messageSerializer);
	}

	public static void registerClass(Class<?> clazz, int id, Codec codec) {
		registerClassAndId(id, clazz, codec);
	}

}
