package com.kingston.mmorpg.framework.net.socket;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.framework.net.socket.serializer.ByteBuffUtils;
import com.kingston.mmorpg.framework.net.socket.serializer.Serializer;
import com.kingston.mmorpg.framework.util.ClassScanner;

import io.netty.buffer.ByteBuf;

public class MessageFactory {

	private Map<Integer, Class<? extends Message>> message2Class = new HashMap<>();

	private static MessageFactory instance = new MessageFactory();

	private Map<Integer, Class<? extends Message>> id2Clazz = new HashMap<>();

	private Map<Class<?>, Integer> clazz2Id = new HashMap<>();
	


	public static MessageFactory getInstance() {
		return instance;
	}

	public void init() {
		Set<Class<?>> messages = ClassScanner.listAllSubclasses("com.kingston.mmorpg", Message.class);
		for (Class<?> clazz : messages) {
			MessageMeta meta = clazz.getAnnotation(MessageMeta.class);
			if (meta == null) {
				throw new RuntimeException("messages[" + clazz.getSimpleName() + "] missed MessageMeta annotation");
			}
			Integer key = buildKey(meta.module(), meta.cmd());
			if (id2Clazz.containsKey(key)) {
				throw new RuntimeException("message meta [" + key + "] duplicate！！");
			}
			clazz2Id.put(clazz, key);
			id2Clazz.put(key, (Class<? extends Message>) clazz);
			Serializer.registerClass(clazz, key);
		}
	}
	
	

	/**
	 * 返回消息的模板class
	 * 
	 * @param module
	 * @param cmd
	 * @return
	 */
	public Class<? extends Message> getMessageMeta(short module, short cmd) {
		Integer key = buildKey(module, cmd);
		return getMessageMeta(key);
	}

	public Class<? extends Message> getMessageMeta(Integer key) {
		return id2Clazz.get(key);
	}

	public int getMessageId(Class<?> clazz) {
		return clazz2Id.get(clazz);
	}
	
	public void writeClass(ByteBuf out, Message message) {
		SerializerMeta meta = Serializer.getSerializerMeta(message.getClass());
		ByteBuffUtils.writeInt(out, meta.getId());
	}

	public void writeMessage(ByteBuf out, Message message) throws Exception {
		Class<?> clazz = message.getClass();
		MessageMeta annotation = (MessageMeta) clazz.getAnnotation(MessageMeta.class);
		short module = annotation.module();
		short cmd = annotation.cmd();
		out.writeShort(module);
		out.writeShort(cmd);

		Serializer serializer = Serializer.getSerializer(message.getClass());
		serializer.encode(out, message);
	}

	private int buildKey(short module, short cmd) {
		int key = module * 10000 + cmd;
		return key;
	}

}
