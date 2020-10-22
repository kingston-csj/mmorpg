package com.kingston.mmorpg.framework.net.socket;

import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.framework.net.socket.netty.NettySession;
import com.kingston.mmorpg.framework.net.task.IDispatch;
import com.kingston.mmorpg.game.scene.actor.Player;
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
     * 修改属性值
     *
     * @param key
     * @return
     */
    Object getAttribute(String key);


    IDispatch getDispatcher();

    void bindDispatcher(IDispatch dispatcher);

    Player getPlayer();

    void setPlayer(Player player);

}