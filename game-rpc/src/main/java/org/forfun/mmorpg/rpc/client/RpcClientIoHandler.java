package org.forfun.mmorpg.rpc.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.forfun.mmorpg.rpc.codec.ProtostuffCodecUtil;
import org.forfun.mmorpg.rpc.data.RpcDataPackage;
import org.forfun.mmorpg.rpc.data.RpcResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@ChannelHandler.Sharable
public class RpcClientIoHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(RpcClientIoHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        RpcDataPackage packet = (RpcDataPackage) msg;

        final Channel channel = context.channel();

        RpcResponseData responseData = ProtostuffCodecUtil.deserializer(packet.getData(), RpcResponseData.class);
        CallBackService.getInstance().fillCallBack(responseData.getIndex(), responseData);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
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
