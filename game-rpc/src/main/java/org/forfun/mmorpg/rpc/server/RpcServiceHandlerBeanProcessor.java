package org.forfun.mmorpg.rpc.server;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class RpcServiceHandlerBeanProcessor  implements BeanPostProcessor {

    @Autowired
    private RpcServiceRegistry handlerRegistry;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        try {
            Class<?> clz = bean.getClass();
            if (clz.getAnnotation(RpcService.class) != null) {
                handlerRegistry.register(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}
