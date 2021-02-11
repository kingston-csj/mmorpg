package com.kingston.mmorpg.framework.net.socket.netty;

import com.kingston.mmorpg.game.base.GameContext;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

@Sharable
public class IdleEventHandler extends ChannelDuplexHandler {

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		// 心跳包检测读超时
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent e = (IdleStateEvent) evt;
			if (e.state() == IdleState.ALL_IDLE) {
				GameContext.getSessionManager().ungisterPlayerChannel(ctx.channel());
			}
		}
	}

}
