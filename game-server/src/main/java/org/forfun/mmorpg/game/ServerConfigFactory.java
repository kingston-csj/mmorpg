package org.forfun.mmorpg.game;

import jakarta.annotation.PostConstruct;
import org.forfun.mmorpg.game.base.GameContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;

@Configuration
public class ServerConfigFactory {

    @Autowired
    private StandardEnvironment environment;

    @PostConstruct
    public void init() throws IOException {
        // 创建YAML加载器
        YamlPropertySourceLoader yamlLoader = new YamlPropertySourceLoader();

        // 根据服务器类型加载对应YAML配置
        switch (GameContext.serverType) {
            case GATE -> addYamlPropertySource(yamlLoader, "config/gate.yml", "gateConfig");
            case GAME -> addYamlPropertySource(yamlLoader, "config/server.yml", "serverConfig");
            case CENTRE -> addYamlPropertySource(yamlLoader, "config/center.yml", "centerConfig");
        }

        // 加载公共YAML配置
        addYamlPropertySource(yamlLoader, "config/common.yml", "commonConfig");
    }

    /**
     * 加载YAML配置文件并添加到环境变量中
     *
     * @param loader     YAML加载器
     * @param filePath   配置文件路径
     * @param sourceName 配置源名称（用于标识）
     */
    private void addYamlPropertySource(YamlPropertySourceLoader loader, String filePath, String sourceName) throws IOException {
        // 加载YAML文件并转换为PropertySource
        PropertySource<?> yamlSource = loader.load(
                sourceName,
                new FileSystemResource(filePath)
        ).get(0); // YAML可能包含多个文档，这里取第一个

        // 添加到环境配置中
        environment.getPropertySources().addLast(yamlSource);
    }

}
