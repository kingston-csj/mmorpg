package com.kingston.mmorpg.net.socket;

import com.kingston.mmorpg.net.dispatcher.IDispatch;
import com.kingston.mmorpg.net.message.Message;
import com.kingston.mmorpg.net.socket.netty.NettySession;
import io.netty.channel.Channel;

/**
 * 玩家登录session，不与任何nio框架绑定
 *
 * @author kingston
 * @see NettySession
 * @see Channel
 */
public interface IdSession {


    void sendPacket(Message packet);

    long getOwnerId();

    /**
     * 更新属性值
     *
     * @param key
     * @param value
     * @return
     */
    Object setAttribute(String key, Object value);

    /**
     * 获取属性值
     *
     * @param key
     * @return
     */
    Object getAttribute(String key);

    IDispatch getDispatcher();

    void bindDispatcher(IDispatch dispatcher);

}