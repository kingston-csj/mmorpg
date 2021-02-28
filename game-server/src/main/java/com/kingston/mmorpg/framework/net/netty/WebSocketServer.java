package com.kingston.mmorpg.framework.net.netty;

import com.kingston.mmorpg.framework.net.ServerNode;
import com.kingston.mmorpg.game.ServerConfig;
import com.kingston.mmorpg.game.base.GameContext;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketServer implements ServerNode {

    private Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    // 避免使用默认线程数参数
    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());

    private int port;

    @Override
    public void init() {
        ServerConfig serverConfig = GameContext.getServerConfig();
        this.port = serverConfig.getWebSocketPort();
    }

    @Override
    public void start() throws Exception {
        if (this.port <= 0) {
            logger.info("webSocket服务暂不开放对外服务");
            return;
        }
        logger.info("webSocket服务已启动，正在监听用户的请求@port:" + port + "......");
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)).childHandler(new WebSocketChannelInitializer());

            serverBootstrap.bind(port).sync();
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

    private class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel arg0) throws Exception {
            ChannelPipeline pipeline = arg0.pipeline();
            // HttpServerCodec: 针对http协议进行编解码
            pipeline.addLast("httpServerCodec", new HttpServerCodec());
            // ChunkedWriteHandler分块写处理，文件过大会将内存撑爆
            pipeline.addLast("chunkedWriteHandler", new ChunkedWriteHandler());
            pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(8192));

            // 用于处理websocket, /ws为访问websocket时的uri
            pipeline.addLast("webSocketServerProtocolHandler", new WebSocketServerProtocolHandler("/ws"));

            pipeline.addLast("myWebSocketHandler", new MyWebSocketHandler());
        }
    }

}
