package com.kingston.mmorpg.framework.net.socket.transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.kingston.mmorpg.common.util.NumberUtil;
import com.kingston.mmorpg.framework.net.socket.ChannelType;
import com.kingston.mmorpg.framework.net.socket.ChannelUtils;
import com.kingston.mmorpg.framework.net.socket.IoSession;
import com.kingston.mmorpg.framework.net.socket.MessageFactory;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.framework.net.socket.message.WebSocketFrame;
import com.kingston.mmorpg.game.base.SpringContext;

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
		
		Class<?> clazz = MessageFactory.getInstance().getMessageMeta(NumberUtil.intValue(frame.getId()));
		Message message = (Message) new Gson().fromJson(frame.getMsg(), clazz);
		System.err.println(message);
		
		IoSession session = ChannelUtils.getSessionBy(channel);
		
		SpringContext.getMessageDispatcher().dispatch(session, message);
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerAdded");
		if (!ChannelUtils.addChannelSession(ctx.channel(), 
				new IoSession(ctx.channel(), ChannelType.WEB_SOCKET))) {
			ctx.channel().close();
			logger.error("Duplicate session,IP=[{}]",ChannelUtils.getIp(ctx.channel()));
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
