
package org.forfun.mmorpg.game;

import org.forfun.mmorpg.framework.net.GameExecutor;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.game.vip.model.VipRight;
import org.forfun.mmorpg.net.ServerNode;
import org.forfun.mmorpg.net.message.MessageFactory;
import org.forfun.mmorpg.net.socket.SocketServerNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * sprint-boot自动bean扫描只能扫描启动类的子目录，所以该类的包路径不能太深
 *
 *
 */
@SpringBootApplication(scanBasePackages = {"org.forfun.mmorpg.framework", "org.forfun.mmorpg.game"})
@EnableCaching
public class ServerStartup implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(ServerStartup.class);

    private List<ServerNode> servers = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(ServerStartup.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

		VipRight vipRight = new VipRight();
		vipRight.setLevel(999);
		vipRight.setExp(123456);
		vipRight.getRewardedIds().addAll(Set.of(1, 2, 3));
		PlayerEnt player = GameContext.getPlayerService().getPlayer(10000L);
		player.setVipRight(vipRight);
		GameContext.getPlayerService().savePlayer(player);
    }

    public void start() throws Exception {
        // 启动网关
        GameContext.getBean(SocketServerNode.class).start();
        // 初始化协议表
        MessageFactory.getInstance().init(ConfigScanPaths.MESSAGE_BASE_PATH);
        // 读取所有角色概括
        GameContext.getPlayerService().loadAllPlayerProfiles();
    }

    @Override
    public void run(String... args) throws Exception {
        final ServerStartup server = new ServerStartup();
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                server.stop();
            }
        });
    }

    public void stop() {
        try {
            for (ServerNode node : servers) {
                node.shutDown();
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        GameExecutor.getInstance().shutDown();
    }

}

