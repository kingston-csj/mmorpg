package com.kingston.mmorpg.framework.net.socket.netty;

import com.kingston.mmorpg.framework.net.socket.*;
import com.kingston.mmorpg.game.base.GameContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.framework.net.socket.message.WebSocketFrame;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	private static Logger logger = LoggerFactory.getLogger(MyWebSocketHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		Channel channel = ctx.channel();
		System.out.println(channel.remoteAddress() + ": " + msg.text());
		WebSocketFrame frame = new Gson().fromJson(msg.text(), WebSocketFrame.class);

		short cmd = Short.valueOf( frame.getId());
		Class<?> clazz = MessageFactory.getInstance().getMessageMeta(cmd);
		Message message = (Message) new Gson().fromJson(frame.getMsg(), clazz);
		System.err.println(message);

		IdSession session = ChannelUtils.getSessionBy(channel);

		GameContext.getMessageDispatcher().dispatch(session, message);
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerAdded");
		if (!ChannelUtils.addChannelSession(ctx.channel(), new NettySession(ctx.channel(), ChannelType.WEB_SOCKET))) {
			ctx.channel().close();
			logger.error("Duplicate session,IP=[{}]", ChannelUtils.getIp(ctx.channel()));
		}
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerRemoved");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//		ctx.channel().close();
		logger.error("exceptionCaught", cause);
	}
}
