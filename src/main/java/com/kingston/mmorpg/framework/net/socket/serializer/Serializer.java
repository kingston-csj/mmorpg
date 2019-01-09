package com.kingston.mmorpg.framework.net.socket.serializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.kingston.mmorpg.framework.net.socket.MessageFactory;
import com.kingston.mmorpg.framework.net.socket.SerializerMeta;

import io.netty.buffer.ByteBuf;

public abstract class Serializer {

	private static Map<Class<?>, Serializer> class2Serializers = new ConcurrentHashMap<>();

	private final static MessageSerializer messageSerializer = new MessageSerializer();

	public static Map<Integer, SerializerMeta> idMetas = new HashMap<>();

	public static Map<Class<?>, SerializerMeta> classMetas = new HashMap<>();

	private static int nextId = -1;

	static {
		register(Boolean.TYPE, new BooleanSerializer());
		register(Boolean.class, new BooleanSerializer());
		register(Byte.TYPE, new ByteSerializer());
		register(Byte.class, new ByteSerializer());
		register(Short.TYPE, new ShortSerializer());
		register(Short.class, new ShortSerializer());
		register(Integer.TYPE, new IntSerializer());
		register(Integer.class, new IntSerializer());
		register(Float.TYPE, new FloatSerializer());
		register(Float.class, new FloatSerializer());
		register(Double.TYPE, new DoubleSerializer());
		register(Double.class, new DoubleSerializer());
		register(Long.TYPE, new LongSerializer());
		register(Long.class, new LongSerializer());
		register(String.class, new StringSerializer());
		register(List.class, new CollectionSerializer());
		register(Set.class, new CollectionSerializer());
		register(Object[].class, new ArraySerializer());
	}

	public static void register(Class<?> clazz, Serializer serializer) {
		int id = nextId();
		registerClassAndId(id, clazz, serializer);
	}

	private static synchronized int nextId() {
		return nextId--;
	}

	public static Serializer getSerializer(Class<?> clazz) {
		if (class2Serializers.containsKey(clazz)) {
			return class2Serializers.get(clazz);
		}
		return getSerializerMeta(clazz).getSerializer();
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

	public static SerializerMeta registerClassAndId(int id, Class<?> clazz, Serializer serializer) {
		SerializerMeta meta = new SerializerMeta(serializer, clazz, id);
		idMetas.put(id, meta);
		classMetas.put(clazz, meta);
		serializer.onRegister(clazz);
		return meta;
	}

	public abstract Object decode(ByteBuf in, Class<?> type);

	public abstract void encode(ByteBuf out, Object value);

	public void onRegister(Class<?> clazz) {

	}

	public static void registerClass(Class<?> clazz, int id) {
		registerClass(clazz, id, messageSerializer);
	}

	public static void registerClass(Class<?> clazz, int id, Serializer serializer) {
		registerClassAndId(id, clazz, serializer);
	}

	public static SerializerMeta readClass(ByteBuf in) {
		int id = ByteBuffUtils.readInt(in);
		return idMetas.get(id);
	}

	public static Object readClassAndObject(ByteBuf in) {
		SerializerMeta meta = readClass(in);
		return meta.getSerializer().decode(in, meta.getType());
	}

	public static SerializerMeta writeClass(ByteBuf out, Class<?> clazz) {
		SerializerMeta meta = classMetas.get(clazz);
		ByteBuffUtils.writeInt(out, meta.getId());
		return meta;
	}

	public static void writeClassAndObject(ByteBuf out, Object object) {
		SerializerMeta meta = writeClass(out, object.getClass());
		meta.getSerializer().encode(out, object);
	}

}
