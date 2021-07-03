package org.forfun.mmorpg.framework.net.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.net.socket.IdSession;
import org.forfun.mmorpg.net.socket.netty.ChannelType;
import org.forfun.mmorpg.net.socket.netty.ChannelUtils;
import org.forfun.mmorpg.net.socket.netty.NettySession;
import org.forfun.mmorpg.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@ChannelHandler.Sharable
public class IoEventHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(IoEventHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (!ChannelUtils.addChannelSession(ctx.channel(),
                new NettySession(GameContext.getMessageSerializer(), ctx.channel(), ChannelType.SOCKET))) {
            ctx.channel().close();
            logger.error("Duplicate session,IP=[{}]", ChannelUtils.getIp(ctx.channel()));
            return;
        }
        IdSession session = ChannelUtils.getSessionBy(ctx.channel());
        GameContext.getMessageDispatcher().onSessionClosed(session);
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        Message packet = (Message) msg;
        logger.info("receive pact, content is {}", packet.getClass().getSimpleName());

        final Channel channel = context.channel();
        IdSession session = ChannelUtils.getSessionBy(channel);

        GameContext.getMessageDispatcher().dispatch(session, packet);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        GameContext.getSessionManager().unRegisterPlayerChannel(ctx.channel());
        IdSession session = ChannelUtils.getSessionBy(ctx.channel());
        GameContext.getMessageDispatcher().onSessionClosed(session);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        if (channel.isActive() || channel.isOpen()) {
            ctx.close();
        }
        if (!(cause instanceof IOException)) {
            logger.error("remote:" + channel.remoteAddress(), cause);
        }
    }

}