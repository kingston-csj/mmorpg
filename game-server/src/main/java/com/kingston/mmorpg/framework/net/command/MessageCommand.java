package com.kingston.mmorpg.framework.net.command;

import com.kingston.mmorpg.framework.net.socket.IdSession;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public final class MessageCommand extends BaseCommand {

	private static Logger logger = LoggerFactory.getLogger(MessageCommand.class);

	private IdSession session;
	/** message controller */
	private Object handler;
	/** target method of the controller */
	private Method method;
	/** arguments passed to the method */
	private Object[] params;

	public static MessageCommand valueOf(IdSession session, int dispatchMap, Object handler, Method method, Object[] params) {
		MessageCommand task = new MessageCommand();
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
