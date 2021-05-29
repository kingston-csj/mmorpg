package org.forfun.mmorpg.net.socket.netty;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.forfun.mmorpg.net.dispatcher.IDispatch;
import org.forfun.mmorpg.protocol.MessageFactory;
import org.forfun.mmorpg.net.message.WebSocketFrame;
import org.forfun.mmorpg.protocol.codec.SerializerFactory;
import org.forfun.mmorpg.net.socket.IdSession;
import org.forfun.mmorpg.net.socket.SessionCloseReason;
import org.forfun.mmorpg.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 链接的会话
 */
public class NettySession implements IdSession {

    private static final Logger logger = LoggerFactory.getLogger(NettySession.class);

    /**
     * 网络连接channel
     */
    private Channel channel;

    /**
     * ip地址
     */
    private String ipAddr;

    private IDispatch dispatcher;

    private SerializerFactory messageSerializer;

    /**
     * 拓展用，保存一些个人数据
     */
    private Map<String, Object> attrs = new HashMap<>();

    private ChannelType channelType;

    public NettySession() {

    }

    public NettySession(SerializerFactory messageSerializer, Channel channel, ChannelType channelType) {
        this.messageSerializer = messageSerializer;
        this.channel = channel;
        this.ipAddr = ChannelUtils.getIp(channel);
        this.dispatcher = anonymousDispatcher;
        this.channelType = channelType;
    }

    @Override
    public void sendPacket(Message packet) {
        if (packet == null) {
            return;
        }
        try {
            if (channelType == ChannelType.SOCKET) {
                // ----------------消息协议格式-------------------------
                // packetLength |cmd   | body
                // short         short    byte[]
                // 其中 packetLength长度占2位
                short cmd = MessageFactory.getInstance().getMessageId(packet.getClass());
                byte[] body = messageSerializer.getEncoder().writeMessageBody(packet);
                int size = 2 + body.length;
                ByteBuf send = Unpooled.buffer(size);
                send.writeInt(size);
                // 写入cmd类型
                send.writeShort(cmd);
                send.writeBytes(body);
                channel.writeAndFlush(send);
            } else {
                WebSocketFrame frame = WebSocketFrame.valueOf(packet);
                channel.writeAndFlush(new TextWebSocketFrame(new Gson().toJson(frame)));
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Override
    public void sendPacket(byte[] data) {
        ByteBuf send = Unpooled.buffer(data.length);
        send.writeBytes(data);
        channel.writeAndFlush(send);
    }

    @Override
    public long getOwnerId() {
        return 0;
    }

    @Override
    public Object setAttribute(String key, Object value) {
        return this.attrs.put(key, value);
    }

    @Override
    public Object getAttribute(String key) {
        return this.attrs.get(key);
    }

    public IDispatch getDispatcher() {
        return dispatcher;
    }

    public void bindDispatcher(IDispatch dispatcher) {
        this.dispatcher = dispatcher;
    }

    public boolean isClose() {
        if (channel == null) {
            return true;
        }
        return !channel.isActive() || !channel.isOpen();
    }

    /**
     * 关闭session
     *
     * @param reason {@link SessionCloseReason}
     */
    public void close(SessionCloseReason reason) {
        try {
            if (this.channel == null) {
                return;
            }
            if (channel.isOpen()) {
                channel.close();
                logger.info("close session[{}], reason is {}", getOwnerId(), reason);
            } else {
                logger.info("session[{}] already close, reason is {}", getOwnerId(), reason);
            }
        } catch (Exception e) {
        }
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

}
