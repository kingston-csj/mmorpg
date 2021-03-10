package org.forfun.mmorpg.net.socket.mina;

import org.forfun.mmorpg.net.dispatcher.IDispatch;
import org.forfun.mmorpg.net.message.Message;
import org.forfun.mmorpg.net.socket.IdSession;
import org.apache.mina.core.session.IoSession;

import java.util.HashMap;
import java.util.Map;

public class MinaSession implements IdSession {

    private IoSession session;

    /** 拓展用，保存一些个人数据  */
    private Map<String, Object> attrs = new HashMap<>();

    public MinaSession(IoSession session) {
        this.session = session;
    }

    @Override
    public void sendPacket(Message packet) {
        session.write(packet);
    }

    @Override
    public long getOwnerId() {
        return 0L;
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
        return null;
    }

    @Override
    public void bindDispatcher(IDispatch dispatcher) {

    }

}
