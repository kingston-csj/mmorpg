package org.forfun.mmorpg.net.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.forfun.mmorpg.net.HostPort;
import org.forfun.mmorpg.net.dispatcher.IMessageDispatcher;
import org.forfun.mmorpg.protocol.codec.SerializerFactory;
import org.forfun.mmorpg.net.socket.IdSession;
import org.forfun.mmorpg.net.socket.netty.ChannelType;
import org.forfun.mmorpg.net.socket.netty.NettyPacketDecoder;
import org.forfun.mmorpg.net.socket.netty.NettySession;

import java.net.InetSocketAddress;

public class RpcClientFactory {

    private IMessageDispatcher messageDispatcher;

    private SerializerFactory messageSerializer;

    private EventLoopGroup group = new NioEventLoopGroup(4);

    public RpcClientFactory(IMessageDispatcher messageDispatcher, SerializerFactory messageSerializer) {
        this.messageDispatcher = messageDispatcher;
        this.messageSerializer = messageSerializer;
    }

    public IdSession createSession(HostPort hostPort) throws Exception {
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel arg0) throws Exception {
                    ChannelPipeline pipeline = arg0.pipeline();
                    pipeline.addLast(new NettyPacketDecoder(messageSerializer.getDecoder()));
//                    pipeline.addLast(new NettyPacketEncoder(messageSerializer.getEncoder()));
                    pipeline.addLast((new MsgIoHandler(messageDispatcher, messageSerializer)));
                }

            });

            ChannelFuture f = b.connect(new InetSocketAddress(hostPort.getHost(), hostPort.getPort())).sync();
            IdSession session = new NettySession(messageSerializer, f.channel(), ChannelType.SOCKET);
            return session;
        } catch (Exception e) {
            e.printStackTrace();
            group.shutdownGracefully();
            throw e;
        }
    }
}
