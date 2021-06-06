package org.forfun.mmorpg.net.socket;

import io.netty.channel.Channel;
import org.forfun.mmorpg.net.dispatcher.IDispatch;
import org.forfun.mmorpg.net.socket.netty.NettySession;
import org.forfun.mmorpg.protocol.Message;

/**
 * 玩家登录session，不与任何nio框架绑定
 *
 * @see org.forfun.mmorpg.net.socket.mina.MinaSession
 * @see NettySession
 * @see Channel
 */
public interface IdSession {

    /**
     * 向客户端发送消息
     *
     * @param packet
     */
    void sendPacket(Message packet);

    /**
     * 向客户端发送消息
     *
     * @param packet
     */
    void sendPacket(byte[] packet);

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

    void close(SessionCloseReason reason);

    boolean isValid();

}