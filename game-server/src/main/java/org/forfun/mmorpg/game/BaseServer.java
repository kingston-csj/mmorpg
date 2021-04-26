package org.forfun.mmorpg.game;

import org.forfun.mmorpg.framework.net.GameExecutor;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.game.vip.model.VipRight;
import org.forfun.mmorpg.net.message.MessageFactory;
import org.forfun.mmorpg.net.message.codec.Preprocessed;
import org.forfun.mmorpg.net.message.codec.SerializerFactory;
import org.forfun.mmorpg.net.socket.SocketServerNode;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class BaseServer {

    public void start() throws Exception {
        initFramework();

        initLogic();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> stop()));
    }

    private void initFramework() throws Exception {
        // 启动网关
        GameContext.getBean(SocketServerNode.class).start();
        // 初始化协议表
        MessageFactory.getInstance().init(ConfigScanPaths.MESSAGE_BASE_PATH);

        SerializerFactory serializerFactory = GameContext.getBean(SerializerFactory.class);
        // 编解码预编译
        if (serializerFactory instanceof Preprocessed) {
            ((Preprocessed) serializerFactory).preCompile();
        }
    }

    private void initLogic() {
        VipRight vipRight = new VipRight();
        vipRight.setLevel(999);
        vipRight.setExp(123456);
        vipRight.getRewardedIds().addAll(Set.of(1, 2, 3));
        PlayerEnt player = GameContext.getPlayerService().getPlayer(10000L);
        player.setVipRight(vipRight);
        GameContext.getPlayerService().savePlayer(player);
    }

    private static void stop() {
        GameExecutor.getInstance().shutDown();
    }
}
