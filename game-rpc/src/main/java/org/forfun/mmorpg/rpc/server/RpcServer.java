package org.forfun.mmorpg.rpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.forfun.mmorpg.rpc.codec.ProtocolDecoder;
import org.forfun.mmorpg.rpc.codec.ProtocolEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class RpcServer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private int CORE_SIZE = Runtime.getRuntime().availableProcessors();
    // 避免使用默认线程数参数
    private EventLoopGroup bossGroup = useEpoll() ? new EpollEventLoopGroup(1) : new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = useEpoll() ? new EpollEventLoopGroup(CORE_SIZE) : new NioEventLoopGroup(CORE_SIZE);

    private RpcServiceRegistry rpcRegistry;

    private RpcServerOptions rpcOptions;

    public RpcServer(RpcServerOptions serverOptions, RpcServiceRegistry rpcRegistry) {
        this.rpcOptions = serverOptions;
        this.rpcRegistry = rpcRegistry;
    }


    private boolean useEpoll() {
        return "linux".equalsIgnoreCase(System.getProperty("os.name"))
                && Epoll.isAvailable();
    }

    public void start() throws Exception {
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());
            System.out.println("rpc服务已启动，正在监听用户的请求@port:" + rpcOptions.getPort() + "......");
            b.bind(new InetSocketAddress(rpcOptions.getPort())).sync();
        } catch (Exception e) {
            logger.error("", e);
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

            throw e;
        }
    }

    public void shutDown() throws Exception {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel arg0) throws Exception {
            ChannelPipeline pipeline = arg0.pipeline();
            pipeline.addLast(new ProtocolEncoder());
            pipeline.addLast(new ProtocolDecoder());
            pipeline.addLast(new RpcServerIoHandler(rpcRegistry));
        }
    }

}
