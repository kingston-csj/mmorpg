package org.forfun.mmorpg.framework.net.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.net.socket.IdSession;
import org.forfun.mmorpg.net.socket.mina.MinaSession;
import org.forfun.mmorpg.net.socket.mina.MinaSessionProperties;
import org.forfun.mmorpg.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerSocketIoHandler extends IoHandlerAdapter  {

    private static Logger logger = LoggerFactory.getLogger(ServerSocketIoHandler.class);

    @Override
    public void sessionCreated(IoSession session) {
        IdSession userSession = new MinaSession(session);
        session.setAttribute(MinaSessionProperties.UserSession,
                userSession);
        GameContext.getMessageDispatcher().onSessionCreated(userSession);
    }

    @Override
    public void messageReceived(IoSession session, Object data) throws Exception {
        Message message = (Message)data;
        IdSession userSession = (IdSession) session.getAttribute(MinaSessionProperties.UserSession);
        //交由消息分发器处理
        GameContext.getMessageDispatcher().dispatch(userSession, message);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        IdSession userSession = (IdSession) session.getAttribute(MinaSessionProperties.UserSession);
        GameContext.getMessageDispatcher().onSessionClosed(userSession);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        logger.error("server exception", cause);
    }
}
