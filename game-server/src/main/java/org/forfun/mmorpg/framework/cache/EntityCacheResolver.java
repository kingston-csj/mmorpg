package org.forfun.mmorpg.framework.cache;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * 自定义放入到{@link CacheManager}中的缓存名称
 */
@Component("entityCacheResolver")
public class EntityCacheResolver implements CacheResolver {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
        Object target = context.getTarget();

        // 从目标对象获取实体类型
        if (target instanceof BaseEntityCacheService) {
            BaseEntityCacheService<?, ?> service = (BaseEntityCacheService<?, ?>) target;
            String cacheName = service.getCacheName();
            return Collections.singletonList(cacheManager.getCache(cacheName));
        }

        // 默认处理
        return context.getOperation().getCacheNames().stream()
                .map(cacheManager::getCache)
                .collect(Collectors.toList());
    }
}