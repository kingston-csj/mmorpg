package com.kingston.mmorpg.framework.net.socket;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.framework.net.socket.serializer.Serializer;
import com.kingston.mmorpg.framework.util.ClassScanner;

import io.netty.buffer.ByteBuf;

public class MessageFactory {

	private Map<String, Class<? extends Message>> message2Class = new HashMap<>();
	
	private static MessageFactory instance = new MessageFactory();
	
	private Map<String, Class<? extends Message>> id2Clazz = new HashMap<>();

	private Map<Class<?>, String> clazz2Id = new HashMap<>();
	
	public static MessageFactory getInstance() {
		return instance;
	}
	
	public void init() {
		Set<Class<?>> messages = ClassScanner.listAllSubclasses("com.kingston.mmorpg.game", Message.class);
		for (Class<?> clazz: messages) {
			MessageMeta meta = clazz.getAnnotation(MessageMeta.class);
			if (meta == null) {
				throw new RuntimeException("messages["+clazz.getSimpleName()+"] missed MessageMeta annotation");
			}
			String key = buildKey(meta.module() , meta.cmd());
			if (id2Clazz.containsKey(key)) {
				throw new RuntimeException("message meta ["+key+"] duplicate！！");
			}
			id2Clazz.put(key,(Class<? extends Message>) clazz);
			clazz2Id.put(clazz, key);
		}
	}

//	@Override
//	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//		Map<String, Object> beans = applicationContext.getBeansWithAnnotation(MessageMeta.class);
//
//		for (Map.Entry<String, Object> entry : beans.entrySet()) {
//			Object message = entry.getValue();
//			Class clazz = message.getClass();
//			MessageMeta annotation = (MessageMeta) clazz.getAnnotation(MessageMeta.class);
//			short module = annotation.module();
//			short cmd = annotation.cmd();
//			String key = getKey(module, cmd);
//			if (message2Class.containsKey(key)) {
//				throw new IllegalStateException("模块号[" + key + "]冲突");
//			}
//			message2Class.put(key, clazz);
//		}
//	}

	/**
	 * 返回消息的模板class
	 * 
	 * @param module
	 * @param cmd
	 * @return
	 */
	public Class<? extends Message> getMessageMeta(short module, short cmd) {
		String key = buildKey(module, cmd);
		return getMessageMeta(key);
	}

	public Class<? extends Message> getMessageMeta(String key) {
		return id2Clazz.get(key);
	}

	public void writeMessage(ByteBuf out, Message message) throws Exception {
		Class<?> clazz = message.getClass();
		MessageMeta annotation = (MessageMeta) clazz.getAnnotation(MessageMeta.class);
		short module = annotation.module();
		short cmd = annotation.cmd();
		out.writeShort(module);
		out.writeShort(cmd);

		Serializer serializer = Serializer.getSerializer(message.getClass());
		serializer.encode(out, message, null);
	}

	private String buildKey(short module, short cmd) {
		String key = module + "_" + cmd;
		return key;
	}

}
