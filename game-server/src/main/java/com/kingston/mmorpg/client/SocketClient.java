package com.kingston.mmorpg.client;

import java.net.InetSocketAddress;

import com.kingston.mmorpg.client.net.ClientPacketDecoder;
import com.kingston.mmorpg.client.net.ClientPacketEncoder;
import com.kingston.mmorpg.client.net.ClientTransportHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;

public class SocketClient {

	public void start() {
		try {
			connect(ClientConfigs.REMOTE_SERVER_IP, ClientConfigs.REMOTE_SERVER_PORT);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void connect(String host, int port) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup(1);
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel arg0) throws Exception {
					ChannelPipeline pipeline = arg0.pipeline();
					pipeline.addLast(new ClientPacketDecoder(1024 * 10, 0, 2, 0, 2));
					pipeline.addLast(new LengthFieldPrepender(2));
					pipeline.addLast(new ClientPacketEncoder());
					pipeline.addLast(new ClientTransportHandler());
				}

			});

			ChannelFuture f = b.connect(new InetSocketAddress(host, port)).sync();
//			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
			group.shutdownGracefully();
		}
	}

}
