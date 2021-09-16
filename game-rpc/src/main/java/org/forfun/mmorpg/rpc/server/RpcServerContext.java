package org.forfun.mmorpg.rpc.server;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RpcServerContext implements ApplicationContextAware {

    private static RpcServerContext self;

    /** spring容器上下文 */
    private static ApplicationContext applicationContext = null;

    @PostConstruct
    private void init() {
        self = this;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        self.applicationContext = applicationContext;
    }

    public final static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

}
