package org.forfun.mmorpg.mq.consume;


import org.forfun.mmorpg.mq.MqMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class MqMessageHandlerBeanProcessor implements BeanPostProcessor, ApplicationContextAware, Ordered {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MqHandlerDispatcher messageDispatcher;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        try {
            Class<?> clz = bean.getClass();
            Method[] methods = clz.getDeclaredMethods();
            for (Method method : methods) {
                MqHandler handler = method.getAnnotation(MqHandler.class);
                if (handler != null) {
                    Class methodArgs = getRequestMethodParam(method);
                    MqHandlerUnit unit = new MqHandlerUnit();
                    unit.setHandler(bean);
                    unit.setMethod(method);
                    method.setAccessible(true);
                    messageDispatcher.registerMethodInvoke(methodArgs.getSimpleName(), unit);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }

        return bean;
    }

    private Class getRequestMethodParam(Method method) {
        Class<?>[] parameters = method.getParameterTypes();
        for (Class<?> parameter : parameters) {
            if (MqMessage.class.isAssignableFrom(parameter)) {
                return parameter;
            }
        }
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

}