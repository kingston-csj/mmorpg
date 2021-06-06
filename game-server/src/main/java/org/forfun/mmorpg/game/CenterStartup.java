package org.forfun.mmorpg.game;

import org.apache.commons.lang3.time.StopWatch;
import org.forfun.mmorpg.game.base.GameContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Center Server entrance
 */
@SpringBootApplication(scanBasePackages = {"org.forfun.mmorpg.framework", "org.forfun.mmorpg.game"})
@EnableScheduling
public class CenterStartup {

    private static Logger logger = LoggerFactory.getLogger(CenterStartup.class);

    public static void main(String[] args) throws Exception {
        GameContext.serverType = ServerType.CENTRE;

        logger.error("[{}]启动开始", GameContext.serverType.name);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        SpringApplication app = new SpringApplication(CenterStartup.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

        GameContext.getBean(BaseServer.class).start();

        stopWatch.stop();
        logger.error("[{}]启动成功，耗时[{}]秒", GameContext.serverType.name, stopWatch.getTime() / 1000);
    }

}

