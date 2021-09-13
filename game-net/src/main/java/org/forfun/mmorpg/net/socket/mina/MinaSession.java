package org.forfun.mmorpg.net.socket.mina;

import org.apache.mina.core.session.IoSession;
import org.forfun.mmorpg.net.dispatcher.IDispatch;
import org.forfun.mmorpg.net.socket.IdSession;
import org.forfun.mmorpg.net.socket.SessionCloseReason;
import org.forfun.mmorpg.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MinaSession implements IdSession {

    private static final Logger logger = LoggerFactory.getLogger(IdSession.class);

    private IoSession session;

    /**
     * 拓展用，保存一些个人数据
     */
    private Map<String, Object> attrs = new HashMap<>();

    private IDispatch dispatcher;

    public MinaSession(IoSession session) {
        this.session = session;
        this.dispatcher = anonymousDispatcher;
    }

    @Override
    public void sendPacket(Message packet) {
        session.write(packet);
    }

    @Override
    public void sendPacket(byte[] packet) {

    }

    @Override
    public Object getAttribute(String key) {
        return attrs.get(key);
    }

    @Override
    public Object setAttribute(String key, Object value) {
        attrs.put(key, value);
        return value;
    }

    @Override
    public IDispatch getDispatcher() {
        return dispatcher;
    }

    @Override
    public void bindDispatcher(IDispatch dispatcher) {
        this.dispatcher = dispatcher;
    }

    /**
     * 匿名分发器，用于角色未登录
     */
    static IDispatch anonymousDispatcher = new IDispatch() {

        @Override
        public int dispatchKey() {
            return 0;
        }

    };

    @Override
    public void close(SessionCloseReason reason) {
        try {
            if (this.session == null) {
                return;
            }
            if (session.isConnected()) {
                session.close();
                logger.info("close session[{}], reason is {}", this, reason);
            } else {
                logger.info("session[{}] already close, reason is {}", this, reason);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Override
    public boolean isValid() {
        return session != null && session.isConnected();
    }
}
