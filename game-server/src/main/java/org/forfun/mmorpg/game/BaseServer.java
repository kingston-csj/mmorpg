package org.forfun.mmorpg.game;

import org.forfun.mmorpg.framework.net.GameExecutor;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.protocol.MessageFactory;
import org.forfun.mmorpg.protocol.codec.Preprocessed;
import org.forfun.mmorpg.protocol.codec.SerializerFactory;
import org.forfun.mmorpg.net.socket.SocketServerNode;
import org.springframework.stereotype.Component;

@Component
public class BaseServer {

    public void start() throws Exception {
        initFramework();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> stop()));
    }

    private void initFramework() throws Exception {
        // NIO socket（多端口）启动
        GameContext.getBean(SocketServerNode.class).start();
        // 初始化协议表
        MessageFactory.getInstance().init(ConfigScanPaths.MESSAGE_BASE_PATH);

        SerializerFactory serializerFactory = GameContext.getBean(SerializerFactory.class);
        // 编解码预编译
        if (serializerFactory instanceof Preprocessed) {
            ((Preprocessed) serializerFactory).preCompile();
        }
    }

    private static void stop() {
        GameExecutor.getInstance().shutDown();
    }
}
