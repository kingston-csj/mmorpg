package org.forfun.mmorpg.game.framework.cache;


import org.forfun.mmorpg.framework.cache.BaseEntityCacheService;
import org.forfun.mmorpg.framework.cache.EntityCacheServiceFactory;
import org.forfun.mmorpg.game.database.user.BaseEntity;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EntityCacheServiceFactoryImpl implements EntityCacheServiceFactory {
    private final Enhancer enhancer = new Enhancer();
    private final Map<Class<? extends BaseEntity<?>>, org.forfun.mmorpg.framework.cache.BaseEntityCacheService<?, ?>> cache = new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public org.forfun.mmorpg.framework.cache.BaseEntityCacheService create(Class entityClass) {
        // 使用 CGLIB 创建动态子类
        enhancer.setSuperclass(org.forfun.mmorpg.framework.cache.BaseEntityCacheService.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                // 处理抽象方法
                if (method.getName().equals("getEntityClass")) {
                    return entityClass;
                }
                if (method.getName().equals("getCacheName")) {
                    return entityClass.getSimpleName();
                }

                // 调用默认实现
                return proxy.invokeSuper(obj, args);
            }
        });

        @SuppressWarnings("unchecked")
        org.forfun.mmorpg.framework.cache.BaseEntityCacheService service =
                (BaseEntityCacheService) enhancer.create();

        // 缓存服务实例
        cache.put(entityClass, service);
        return service;
    }
}