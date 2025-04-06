package org.forfun.mmorpg.game.database.config;

import org.forfun.mmorpg.game.database.config.inject.CommonValueAutoInjectHandler;
import org.forfun.mmorpg.game.database.config.inject.CommonValueReloadListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 静态配置数据仓库
 *
 */
@Component
public class ConfigResourceRegistry implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CommonValueAutoInjectHandler autoInjectHandler = applicationContext.getBean(CommonValueAutoInjectHandler.class);
        Map<String, Object> services = applicationContext.getBeansWithAnnotation(Service.class);
        for (Map.Entry<String, Object> entry : services.entrySet()) {
            autoInjectHandler.postBeanAfterInject(entry.getValue());
        }
        // 对 CommonValueReloadListener实现类进行reload
        for (Map.Entry<String, CommonValueReloadListener> entry : applicationContext.getBeansOfType(CommonValueReloadListener.class).entrySet()) {
            entry.getValue().afterReload();
        }
    }

}
