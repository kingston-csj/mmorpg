package org.forfun.mmorpg.game;

import jforgame.socket.share.ServerNode;
import org.forfun.mmorpg.game.base.GameContext;
import org.springframework.stereotype.Component;

@Component
public class BaseServer {

    public void start() throws Exception {
        initFramework();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> stop()));
    }

    private void initFramework() throws Exception {
        // NIO socket（多端口）启动
        GameContext.getBean(ServerNode.class).start();

//        SerializerFactory serializerFactory = GameContext.getBean(SerializerFactory.class);
//        // 编解码预编译
//        if (serializerFactory instanceof Preprocessed) {
//            ((Preprocessed) serializerFactory).preCompile();
//        }
    }

    private static void stop() {
    }
}
