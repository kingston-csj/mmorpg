package com.kingston.mmorpg.client.net;

import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.game.player.message.ReqPlayerLogin;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientTransportHandler extends ChannelInboundHandlerAdapter {


	public ClientTransportHandler(){

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx){
		Channel channel = ctx.channel();
		
		ReqPlayerLogin login = new ReqPlayerLogin();
		login.setPlayerId(123456L);
		channel.writeAndFlush(login);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		Message packet = (Message)msg;
		System.err.println("---receive----" + packet);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.err.println("客户端关闭1");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		System.err.println("客户端关闭3");
		Channel channel = ctx.channel();
		cause.printStackTrace();
		if(channel.isActive()){
			System.err.println("simpleclient"+channel.remoteAddress()+"异常");
		}
	}
}