package com.kingston.mmorpg.framework.net.socket.task;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kingston.mmorpg.framework.net.socket.IoSession;
import com.kingston.mmorpg.framework.net.socket.message.CmdExecutor;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.game.base.SpringContext;

@Component
public class MessageDispatcher {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/** [module_cmd, CmdExecutor] */
	private static final Map<String, CmdExecutor> MODULE_CMD_HANDLERS = new HashMap<>();


	public static void registerMethodInvoke(String key, CmdExecutor executor) {
		if (MODULE_CMD_HANDLERS.containsKey(key)) {
			throw new RuntimeException(String.format("module[%s] duplicated", key));
		}
		MODULE_CMD_HANDLERS.put(key, executor);
	}

	/**
	 * message entrance, in which io thread dispatch messages
	 * @param session
	 * @param message
	 */
	public void dispatch(IoSession session, Message message) {
		short module = message.getModule();
		short cmd    = message.getCmd();

		CmdExecutor cmdExecutor = MODULE_CMD_HANDLERS.get(buildKey(module, cmd));
		if (cmdExecutor == null) {
			logger.error("message executor missed, module={},cmd={}", module, cmd);
			return;
		}

		Object[] params = convertToMethodParams(session, cmdExecutor.getParams(), message);
		Object controller = cmdExecutor.getHandler();

		IDispatch dipatcher = session.getDipatcher();
		TaskHandlerContext.getInstance().acceptTask(
				CmdTask.valueOf(dipatcher.dispatchMap(), dipatcher.dispatchLine(), 
						controller, cmdExecutor.getMethod(), params));
	}

	/**
	 * 将各种参数转为被RequestMapper注解的方法的实参
	 * @param session
	 * @param methodParams
	 * @param message
	 * @return
	 */
	private Object[] convertToMethodParams(IoSession session, Class<?>[] methodParams, Message message) {
		Object[] result = new Object[methodParams==null?0:methodParams.length];

		for (int i=0;i<result.length;i++) {
			Class<?> param = methodParams[i];
			if (IoSession.class.isAssignableFrom(param)) {
				result[i] = session;
			}else if (Long.class.isAssignableFrom(param)) {
				result[i] = session;
			}else if (long.class.isAssignableFrom(param)) {
				result[i] = SpringContext.getSessionManager().getPlayerIdBy(session);
			}else if (Message.class.isAssignableFrom(param)) {
				result[i] = message;
			}
		}

		return result;
	}

	private String buildKey(short module, short cmd) {
		return module + "_" + cmd;
	}


}
