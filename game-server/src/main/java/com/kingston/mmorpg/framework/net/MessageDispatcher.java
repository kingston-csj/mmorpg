package com.kingston.mmorpg.framework.net;


import com.kingston.mmorpg.game.base.GameContext;
import com.kingston.mmorpg.net.dispatcher.IDispatch;
import com.kingston.mmorpg.net.dispatcher.MessageEvent;
import com.kingston.mmorpg.net.socket.IdSession;
import com.kingston.mmorpg.net.message.MessageFactory;
import com.kingston.mmorpg.net.message.CmdExecutor;
import com.kingston.mmorpg.net.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageDispatcher {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /** [module*100+cmd, CmdExecutor] */
    private static final Map<Short, CmdExecutor> MODULE_CMD_HANDLERS = new HashMap<>();

    public void registerMethodInvoke(short key, CmdExecutor executor) {
        if (MODULE_CMD_HANDLERS.containsKey(key)) {
            throw new RuntimeException(String.format("module[%s] duplicated", key));
        }
        MODULE_CMD_HANDLERS.put(key, executor);
    }

    public void onSessionCreated(IdSession session) {

    }

    /**
     * message entrance, in which io thread dispatch messages
     *
     * @param session
     * @param message
     */
    public void dispatch(IdSession session, Message message) {
        short cmd = MessageFactory.getInstance().getMessageId(message.getClass());

        CmdExecutor cmdExecutor = MODULE_CMD_HANDLERS.get(cmd);
        if (cmdExecutor == null) {
            logger.error("message executor missed, cmd={}", cmd);
            return;
        }

        Object[] params = convertToMethodParams(session, cmdExecutor.getParams(), message);
        Object controller = cmdExecutor.getHandler();

        IDispatch dispatcher = session.getDispatcher();
        GameExecutor.getInstance().acceptTask(MessageEvent.valueOf(session, dispatcher.dispatchKey(),
                controller, cmdExecutor.getMethod(), params));
    }

    /**
     * 将各种参数转为被RequestMapper注解的方法的实参
     *
     * @param session
     * @param methodParams
     * @param message
     * @return
     */
    private Object[] convertToMethodParams(IdSession session, Class<?>[] methodParams, Message message) {
        Object[] result = new Object[methodParams == null ? 0 : methodParams.length];

        for (int i = 0; i < result.length; i++) {
            Class<?> param = methodParams[i];
            if (IdSession.class.isAssignableFrom(param)) {
                result[i] = session;
            } else if (Long.class.isAssignableFrom(param)) {
                result[i] = session;
            } else if (long.class.isAssignableFrom(param)) {
                result[i] = GameContext.getSessionManager().getPlayerIdBy(session);
            } else if (Message.class.isAssignableFrom(param)) {
                result[i] = message;
            }
        }

        return result;
    }


    public void onSessionClosed(IdSession session) {

    }

}

