package org.forfun.mmorpg.game

import jforgame.commons.util.NumberUtil
import org.apache.commons.lang3.time.StopWatch
import org.forfun.mmorpg.game.base.GameContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.core.io.FileSystemResource
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * 游戏主服务器入口
 */
@SpringBootApplication(scanBasePackages = ["org.forfun.mmorpg.framework", "org.forfun.mmorpg.game"])
@EnableCaching
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
open class ServerStartup {

    companion object {
        private val logger = LoggerFactory.getLogger(ServerStartup::class.java)

        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val yamlFactory = YamlPropertiesFactoryBean()
            yamlFactory.setResources(FileSystemResource("config/common.yml")) // 改为yml文件路径
            val commonProperty = yamlFactory.`object` // 获取解析后的Properties对象

            val serverType = ServerType.of(
                commonProperty!!["server.type"] as Int
            )
            GameContext.serverType = serverType

            logger.error("[${GameContext.serverType!!.desc}]启动开始")
            val stopWatch = StopWatch()
            stopWatch.start()

            // 启动Spring应用
            val rawPortVal = commonProperty["server.port"]
            val basePort = NumberUtil.intValue(rawPortVal)
            val realPort = basePort + serverType!!.type

            SpringApplication(ServerStartup::class.java).apply {
                setBannerMode(Banner.Mode.OFF)
                setDefaultProperties(mapOf("server.port" to realPort.toString()))
                run(*args)
            }

            GameContext.getBean(BaseServer::class.java).start()

            // better code ??!!
            val container: ServerLayer? = when (serverType) {
                ServerType.GAME -> GameServerLayer()
                ServerType.CENTRE -> CenterServerLayer()
                ServerType.FIGHT -> FightServerLayer()
                else -> null
            }
            val applicationContext = GameContext.applicationContext
            val configurableApplicationContext = applicationContext as ConfigurableApplicationContext
            val defaultListableBeanFactory = configurableApplicationContext.beanFactory as DefaultListableBeanFactory
            container?.let {
                defaultListableBeanFactory.registerSingleton("serverLayer", it)
                it.init()
            }

            stopWatch.stop()
            logger.error("[${GameContext.serverType!!.desc}]启动成功，耗时[${stopWatch.getTime() / 1000}]秒")
        }
    }
}
