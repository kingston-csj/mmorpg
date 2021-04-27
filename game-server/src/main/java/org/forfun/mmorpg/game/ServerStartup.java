package org.forfun.mmorpg.game;

import org.apache.commons.lang3.time.StopWatch;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.game.vip.model.VipRight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Set;

/**
 * 游戏主服务器入口
 */
@SpringBootApplication(scanBasePackages = {"org.forfun.mmorpg.framework", "org.forfun.mmorpg.game"})
@EnableCaching
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ServerStartup {

    private static Logger logger = LoggerFactory.getLogger(ServerStartup.class);

    public static void main(String[] args) throws Exception {
        GameContext.serverType = ServerType.GAME;

        logger.error("[{}]启动开始", GameContext.serverType.name);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        SpringApplication app = new SpringApplication(ServerStartup.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

        GameContext.getBean(BaseServer.class).start();

        internalLoad();

        stopWatch.stop();
        logger.error("[{}]启动成功，耗时[{}]秒", GameContext.serverType.name, stopWatch.getTime() / 1000);
    }

    private static void internalLoad() {
        VipRight vipRight = new VipRight();
        vipRight.setLevel(999);
        vipRight.setExp(123456);
        vipRight.getRewardedIds().addAll(Set.of(1, 2, 3));
        PlayerEnt player = GameContext.getPlayerService().getPlayer(10000L);
        player.setVipRight(vipRight);
        GameContext.getPlayerService().savePlayer(player);
    }

}

