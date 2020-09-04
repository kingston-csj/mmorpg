package com.kingston.mmorpg.framework.net.task;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kingston.mmorpg.framework.net.socket.MessagePusher;
import com.kingston.mmorpg.framework.net.socket.message.Message;

public final class CmdTask extends BaseTask {

	private static Logger logger = LoggerFactory.getLogger(CmdTask.class);

	/** owner playerId */
	private long playerId;
	/** io message content */
	private Message message;
	/** message controller */
	private Object handler;
	/** target method of the controller */
	private Method method;
	/** arguments passed to the method */
	private Object[] params;

	public static CmdTask valueOf(int dispatchMap, int dispatchLine, Object handler, Method method, Object[] params) {
		CmdTask msgTask = new CmdTask();
		msgTask.dispatchMap = dispatchMap;
		msgTask.handler = handler;
		msgTask.method = method;
		msgTask.params = params;

		return msgTask;
	}

	@Override
	public void action() {
		try {
			Object response = method.invoke(handler, params);
			if (response != null) {
				MessagePusher.pushMessage(playerId, (Message) response);
			}
		} catch (Exception e) {
			logger.error("message task execute failed ", e);
			e.printStackTrace();
		}
	}

	public long getPlayerId() {
		return playerId;
	}

	public Message getMessage() {
		return message;
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
