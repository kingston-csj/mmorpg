package org.forfun.mmorpg.framework.net.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.forfun.mmorpg.framework.net.NodeConfig;
import org.forfun.mmorpg.game.ServerConfig;
import org.forfun.mmorpg.net.message.codec.SerializerFactory;
import org.forfun.mmorpg.net.socket.SocketServerNode;
import org.forfun.mmorpg.net.socket.netty.NettyPacketDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class NettySocketServer implements SocketServerNode {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private boolean useEpoll = "linux".equalsIgnoreCase(System.getProperty("os.name"));

    private int CORE_SIZE = Runtime.getRuntime().availableProcessors();
    // 避免使用默认线程数参数
    private EventLoopGroup bossGroup = useEpoll ? new EpollEventLoopGroup(1) : new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = useEpoll ? new EpollEventLoopGroup(CORE_SIZE) : new NioEventLoopGroup(CORE_SIZE);

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private SerializerFactory serializerFactory;

    private List<NodeConfig> nodeConfigs = new ArrayList<>();

    @Override
    @PostConstruct
    public void init() {
        if (serverConfig.getServerPort() > 0) {
            nodeConfigs.add(NodeConfig.valueOf("*", serverConfig.getServerPort()));
        }
        if (serverConfig.getRpcPort() > 0) {
            nodeConfigs.add(NodeConfig.valueOf("*", serverConfig.getRpcPort()));
        }
    }

    @Override
    public void start() throws Exception {
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new NettySocketServer.ChildChannelHandler());

            for (NodeConfig node : nodeConfigs) {
                logger.info("socket服务已启动，正在监听用户的请求@port:" + node.getPort() + "......");
                b.bind(new InetSocketAddress(node.getPort())).sync();
            }
        } catch (Exception e) {
            logger.error("", e);
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

            throw e;
        }
    }

    @Override
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
            pipeline.addLast(new NettyPacketDecoder(serializerFactory.getDecoder()));
//            pipeline.addLast(new NettyPacketEncoder(serializerFactory.getEncoder()));
            // 客户端300秒没收发包，便会触发UserEventTriggered事件到IdleEventHandler
            pipeline.addLast(new IdleStateHandler(0, 0, 300));
            pipeline.addLast(new IdleEventHandler());
            pipeline.addLast(new IoEventHandler());
        }
    }

}