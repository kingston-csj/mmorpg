package org.forfun.mmorpg.framework.net;


import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.net.dispatcher.IDispatch;
import org.forfun.mmorpg.net.dispatcher.IMessageDispatcher;
import org.forfun.mmorpg.net.dispatcher.MessageEvent;
import org.forfun.mmorpg.net.message.CmdExecutor;
import org.forfun.mmorpg.net.rpc.RpcCallbackResponse;
import org.forfun.mmorpg.net.socket.IdSession;
import org.forfun.mmorpg.protocol.Message;
import org.forfun.mmorpg.protocol.MessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageDispatcher implements IMessageDispatcher {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /** [module*100+cmd, CmdExecutor] */
    private static final Map<Short, CmdExecutor> MODULE_CMD_HANDLERS = new HashMap<>();

    public void registerMethodInvoke(short key, CmdExecutor executor) {
        if (MODULE_CMD_HANDLERS.containsKey(key)) {
            throw new RuntimeException(String.format("module[%s] duplicated", key));
        }
        MODULE_CMD_HANDLERS.put(key, executor);
    }

    @Override
    public void onSessionCreated(IdSession session) {

    }

    @Override
    public void dispatch(IdSession session, Message message) {
        short cmd = MessageFactory.getInstance().getMessageId(message.getClass());
        if (message instanceof RpcCallbackResponse) {
            RpcCallbackResponse callbackMessage = (RpcCallbackResponse) message;
            CallbackHandler.fillCallback(callbackMessage.getIndex(), message);
            return;
        }
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

    @Override
    public void onSessionClosed(IdSession session) {

    }

}

