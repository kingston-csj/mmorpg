package com.kingston.mmorpg.framework.net.socket;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.framework.net.socket.annotation.ModuleMeta;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.framework.net.socket.serializer.ByteBuffUtils;
import com.kingston.mmorpg.framework.net.socket.serializer.Serializer;
import com.kingston.mmorpg.game.util.ClassScanner;

import io.netty.buffer.ByteBuf;

public class MessageFactory {

	private static MessageFactory instance = new MessageFactory();

	private Map<Short, Class<? extends Message>> id2Clazz = new HashMap<>();

	private Map<Class<?>, Short> clazz2Id = new HashMap<>();

	public static MessageFactory getInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	public void init() {
		Set<Class<?>> messages = ClassScanner.listClassesWithAnnotation("com.kingston.mmorpg", ModuleMeta.class);
		for (Class<?> clazz : messages) {
			ModuleMeta meta = clazz.getAnnotation(ModuleMeta.class);
			if (meta == null) {
				throw new RuntimeException("messages[" + clazz.getSimpleName() + "] missed MessageMeta annotation");
			}
			byte module = meta.module();
			
			// facade层所在包名的上一层
			int prevPacketNameIndex = clazz.getPackage().getName().lastIndexOf(".");
			String packetName = clazz.getPackage().getName().substring(0, prevPacketNameIndex);
			Set<Class<?>> msgClazzs = ClassScanner.listAllSubclasses(packetName, Message.class);
			for (Class<?> msgClz : msgClazzs) {
				MessageMeta mapperAnnotation = msgClz.getAnnotation(MessageMeta.class);
				if (mapperAnnotation != null) {
					short cmdMeta = mapperAnnotation.cmd();
					if (Math.abs(cmdMeta) >= 100) {
						throw new RuntimeException("abs(cmd) must less than 100, target " + msgClz.getSimpleName());
					}
					short key = (short)(Math.abs(module) * 100 + cmdMeta);
					if (module < 0) {
						key = (short) (0 - key);
					}
					if (id2Clazz.containsKey(key)) {
						throw new RuntimeException("message meta [" + key + "] duplicate！！");
					}
					clazz2Id.put(msgClz, key);
					id2Clazz.put(key, (Class<? extends Message>) msgClz);
					Serializer.registerClass(msgClz, key);
				}
			}
		}
	}

	/**
	 * 返回消息的模板class
	 * 
	 * @param module
	 * @param cmd
	 * @return
	 */
	public Class<? extends Message> getMessageMeta(short cmd) {
		return id2Clazz.get(cmd);
	}


	public short getMessageId(Class<?> clazz) {
		if (clazz == null  || !clazz2Id.containsKey(clazz)) {
			System.err.print("message null");
		}
		return clazz2Id.get(clazz);
	}

	public void writeClass(ByteBuf out, Message message) {
		SerializerMeta meta = Serializer.getSerializerMeta(message.getClass());
		ByteBuffUtils.writeInt(out, meta.getId());
	}

	public void writeMessage(ByteBuf out, Message message) throws Exception {
		Class<?> clazz = message.getClass();
		short cmd = clazz2Id.get(clazz);
		out.writeShort(cmd);

		Serializer serializer = Serializer.getSerializer(message.getClass());
		serializer.encode(out, message);
	}

}
