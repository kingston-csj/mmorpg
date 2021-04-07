package org.forfun.mmorpg.csv;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class Context  implements ApplicationContextAware {
    private static Context self;

    /** spring容器上下文 */
    private static ApplicationContext applicationContext = null;

    @PostConstruct
    private void init() {
        self = this;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Context.applicationContext = applicationContext;
    }

    @Resource
    public ConversionService serverConfig;

    public final static ConversionService getConversionService() {
        return self.serverConfig;
    }
}
