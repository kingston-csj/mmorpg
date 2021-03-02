package com.kingston.mmorpg.net.dispatcher;

import com.kingston.mmorpg.net.message.Message;
import com.kingston.mmorpg.net.socket.IdSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public final class MessageEvent extends BaseEvent {

	private static Logger logger = LoggerFactory.getLogger(MessageEvent.class);

	private IdSession session;
	/** message controller */
	private Object handler;
	/** target method of the controller */
	private Method method;
	/** arguments passed to the method */
	private Object[] params;

	public static MessageEvent valueOf(IdSession session, int dispatchMap, Object handler, Method method, Object[] params) {
		MessageEvent task = new MessageEvent();
		task.session = session;
		task.dispatchMap = dispatchMap;
		task.handler = handler;
		task.method = method;
		task.params = params;

		return task;
	}

	@Override
	public void action() {
		try {
			Object response = method.invoke(handler, params);
			if (response != null) {
				session.sendPacket((Message) response);
			}
		} catch (Exception e) {
			logger.error("message task execute failed ", e);
		}
	}

	public Object getHandler() {
		return handler;
	}

	public Method getMethod() {
		return method;
	}

	public Object[] getParams() {
		return params;
	}

	@Override
	public String toString() {
		return this.getName() + "[" + handler.getClass().getName() + "@" + method.getName() + "]";
	}

}
