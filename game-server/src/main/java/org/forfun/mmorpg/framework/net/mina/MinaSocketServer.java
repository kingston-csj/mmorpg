package org.forfun.mmorpg.framework.net.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.SimpleIoProcessorPool;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.DefaultSocketSessionConfig;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioProcessor;
import org.apache.mina.transport.socket.nio.NioSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.forfun.mmorpg.game.ServerConfig;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.net.HostPort;
import org.forfun.mmorpg.net.message.codec.SerializerFactory;
import org.forfun.mmorpg.net.socket.SocketServerNode;
import org.forfun.mmorpg.net.socket.mina.MessageCodecFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MinaSocketServer implements SocketServerNode {

    private Logger logger = LoggerFactory.getLogger(MinaSocketServer.class);

    private static final int CPU_CORE_SIZE = Runtime.getRuntime().availableProcessors();

    private static final Executor executor = Executors.newCachedThreadPool();

    private static final SimpleIoProcessorPool<NioSession> pool =
            new SimpleIoProcessorPool<>(NioProcessor.class, executor, CPU_CORE_SIZE);

    private SocketAcceptor acceptor;

    @Autowired
    private SerializerFactory serializerFactory;

    @Autowired
    private ServerConfig serverConfig;

    private List<HostPort> nodes = new ArrayList<>();

    @Override
    @PostConstruct
    public void init() {
        if (serverConfig.getServerPort() > 0) {
            nodes.add(HostPort.valueOf("*", serverConfig.getServerPort()));
        }
        if (serverConfig.getRpcPort() > 0) {
            nodes.add(HostPort.valueOf("*", serverConfig.getRpcPort()));
        }
    }

    /**
     * start Mina serversocket
     * @throws Exception
     */
    @Override
    public void start() throws Exception {
        int serverPort = GameContext.getServerConfig().getServerPort();
        IoBuffer.setUseDirectBuffer(false);
        IoBuffer.setAllocator(new SimpleBufferAllocator());

        acceptor = new NioSocketAcceptor(pool);
        acceptor.setReuseAddress(true);
        acceptor.getSessionConfig().setAll(getSessionConfig());

        logger.info("mina socket server start at port:{},正在监听客户端的连接...", serverPort);
        DefaultIoFilterChainBuilder filterChain = acceptor.getFilterChain();
        filterChain.addLast("codec",
                new ProtocolCodecFilter(new MessageCodecFactory(serializerFactory)));
        //指定业务逻辑处理器
        acceptor.setHandler(new ServerSocketIoHandler());
        //设置端口号
        acceptor.setDefaultLocalAddress(new InetSocketAddress(serverPort));
        //启动监听
        for (HostPort node : nodes) {
            logger.info("socket服务已启动，正在监听用户的请求@port:" + node.getPort() + "......");
            acceptor.bind(new InetSocketAddress(node.getPort()));
        }
    }

    @Override
    public void shutDown() throws Exception {
        if (acceptor != null) {
            acceptor.unbind();
            acceptor.dispose();
        }
        logger.error("---------> socket server stop successfully");
    }

    private SocketSessionConfig getSessionConfig() {
        SocketSessionConfig config = new DefaultSocketSessionConfig();
        config.setKeepAlive(true);
        config.setReuseAddress(true);

        return config;
    }


}