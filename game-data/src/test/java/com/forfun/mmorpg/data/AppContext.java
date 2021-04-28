package com.forfun.mmorpg.data;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AppContext implements ApplicationContextAware {

    private static AppContext self;

    /**
     * spring容器上下文
     */
    private static ApplicationContext applicationContext = null;

    public static <E> E getBean(Class<?> e) {
        return (E) applicationContext.getBean(e);
    }

    @PostConstruct
    private void init() {
        self = this;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContext.applicationContext = applicationContext;
    }

}
