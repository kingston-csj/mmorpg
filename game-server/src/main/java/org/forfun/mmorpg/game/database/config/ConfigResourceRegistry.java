package org.forfun.mmorpg.game.database.config;

import org.forfun.mmorpg.game.database.config.container.ReloadableContainer;
import org.forfun.mmorpg.game.logger.LoggerUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 静态配置数据仓库
 *
 */
@Component
public class ConfigResourceRegistry implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, ReloadableContainer> configs = applicationContext.getBeansOfType(ReloadableContainer.class);
        for (Map.Entry<String, ReloadableContainer> entry : configs.entrySet()) {
            Reloadable config = entry.getValue();
            config.reload();
        }

        // 检查数据是否有问题
        try {
            for (Map.Entry<String, ReloadableContainer> entry : configs.entrySet()) {
                ReloadableContainer container = entry.getValue();
                container.selfChecking();
            }
        } catch (Throwable e) {
            LoggerUtils.error("程序配置异常", e);
            System.exit(-1);
        }
    }

}
