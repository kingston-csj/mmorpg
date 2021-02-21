
package com.kingston.mmorpg.game;

import com.kingston.mmorpg.framework.net.ServerNode;
import com.kingston.mmorpg.framework.net.command.GameExecutor;
import com.kingston.mmorpg.framework.net.socket.MessageFactory;
import com.kingston.mmorpg.framework.net.socket.SocketServerNode;
import com.kingston.mmorpg.game.base.GameContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.ArrayList;
import java.util.List;

/**
 * sprint-boot自动bean扫描只能扫描启动类的子目录，所以该类的包路径不能太深
 *
 * @author kingston
 */
@SpringBootApplication(scanBasePackages = {"com.kingston.mmorpg.framework", "com.kingston.mmorpg.game"})
@EnableCaching
public class ServerStartup implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(ServerStartup.class);

    private List<ServerNode> servers = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(ServerStartup.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

//		VipRight vipRight = new VipRight();
//		vipRight.setLevel(999);
//		vipRight.setExp(123456);
//		vipRight.getRewardedIds().addAll(Set.of(1, 2, 3));
//		Player player = GameContext.getPlayerService().getPlayer(10000L);
//		player.getEntity().setVipRight(vipRight);
//		GameContext.getPlayerService().savePlayer(player);
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

