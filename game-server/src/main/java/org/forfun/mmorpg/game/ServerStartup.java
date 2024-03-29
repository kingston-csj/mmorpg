package org.forfun.mmorpg.game;

import jforgame.commons.NumberUtil;
import org.apache.commons.lang3.time.StopWatch;
import org.forfun.mmorpg.game.base.GameContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Collections;
import java.util.Properties;

/**
 * 游戏主服务器入口
 */
@SpringBootApplication(scanBasePackages = {"org.forfun.mmorpg.framework", "org.forfun.mmorpg.game"})
@EnableCaching
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
public class ServerStartup {

    private static Logger logger = LoggerFactory.getLogger(ServerStartup.class);

    public static void main(String[] args) throws Exception {
        Properties commonProperty = PropertiesLoaderUtils.loadProperties(new FileSystemResource("config/common.properties"));
        ServerType serverType = ServerType.of(NumberUtil.intValue(commonProperty.get("server.type")));

        GameContext.serverType = serverType;

        logger.error("[{}]启动开始", GameContext.serverType.name);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        SpringApplication app = new SpringApplication(ServerStartup.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", NumberUtil.intValue(commonProperty.get("server.port")) + serverType.type));
        app.run(args);

        GameContext.getBean(BaseServer.class).start();

        // better code ??!!
        ServerLayer container = null;
        switch (serverType) {
            case GAME -> container = new GameServerLayer();
            case CENTRE -> container = new CenterServerLayer();
            case FIGHT -> container = new FightServerLayer();
        }
        ApplicationContext applicationContext = GameContext.getApplicationContext();
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        defaultListableBeanFactory.registerSingleton("serverLayer", container);
        container.init();

        stopWatch.stop();
        logger.error("[{}]启动成功，耗时[{}]秒", GameContext.serverType.name, stopWatch.getTime() / 1000);
    }


}

