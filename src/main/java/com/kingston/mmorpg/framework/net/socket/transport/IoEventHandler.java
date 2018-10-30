package com.kingston.mmorpg.framework.net.socket.transport;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kingston.mmorpg.framework.net.socket.ChannelType;
import com.kingston.mmorpg.framework.net.socket.ChannelUtils;
import com.kingston.mmorpg.framework.net.socket.IoSession;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.game.base.SpringContext;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

@Sharable
public class IoEventHandler extends ChannelInboundHandlerAdapter {

	private final static Logger logger = LoggerFactory.getLogger(IoEventHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		if (!ChannelUtils.addChannelSession(ctx.channel(), new IoSession(ctx.channel(), ChannelType.SOCKET))) {
			ctx.channel().close();
			logger.error("Duplicate session,IP=[{}]", ChannelUtils.getIp(ctx.channel()));
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
		Message packet = (Message) msg;
		logger.info("receive pact, content is {}", packet.getClass().getSimpleName());

		final Channel channel = context.channel();
		IoSession session = ChannelUtils.getSessionBy(channel);

		SpringContext.getMessageDispatcher().dispatch(session, packet);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		SpringContext.getSessionManager().ungisterPlayerChannel(ctx.channel());
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

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		// 心跳包检测读超时
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent e = (IdleStateEvent) evt;
			if (e.state() == IdleState.ALL_IDLE) {
				SpringContext.getSessionManager().ungisterPlayerChannel(ctx.channel());
			}
		}
	}

}
