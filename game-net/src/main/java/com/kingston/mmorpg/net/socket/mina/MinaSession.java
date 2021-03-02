package com.kingston.mmorpg.net.socket.mina;

import com.kingston.mmorpg.net.dispatcher.IDispatch;
import com.kingston.mmorpg.net.message.Message;
import com.kingston.mmorpg.net.socket.IdSession;
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
