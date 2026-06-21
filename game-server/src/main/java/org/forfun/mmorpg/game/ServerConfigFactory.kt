package org.forfun.mmorpg.game

import jakarta.annotation.PostConstruct
import org.forfun.mmorpg.game.base.GameContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.env.YamlPropertySourceLoader
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.PropertySource
import org.springframework.core.env.StandardEnvironment
import org.springframework.core.io.FileSystemResource
import java.io.IOException

@Configuration
open class ServerConfigFactory {

    @Autowired
    private lateinit var environment: StandardEnvironment

    @PostConstruct
    @Throws(IOException::class)
    fun init() {
        // 创建YAML加载器
        val yamlLoader = YamlPropertySourceLoader()

        // 根据服务器类型加载对应YAML配置
        when (GameContext.serverType) {
            ServerType.GATE -> addYamlPropertySource(yamlLoader, "config/gate.yml", "gateConfig")
            ServerType.GAME -> addYamlPropertySource(yamlLoader, "config/server.yml", "serverConfig")
            ServerType.CENTRE -> addYamlPropertySource(yamlLoader, "config/center.yml", "centerConfig")
            else -> {}
        }

        // 加载公共YAML配置
        addYamlPropertySource(yamlLoader, "config/common.yml", "commonConfig")
    }

    /**
     * 加载YAML配置文件并添加到环境变量中
     *
     * @param loader     YAML加载器
     * @param filePath   配置文件路径
     * @param sourceName 配置源名称（用于标识）
     */
    @Throws(IOException::class)
    private fun addYamlPropertySource(loader: YamlPropertySourceLoader, filePath: String, sourceName: String) {
        // 加载YAML文件并转换为PropertySource
        val yamlSource = loader.load(
            sourceName,
            FileSystemResource(filePath)
        )[0] // YAML可能包含多个文档，这里取第一个

        // 添加到环境配置中
        environment.propertySources.addLast(yamlSource)
    }
}
