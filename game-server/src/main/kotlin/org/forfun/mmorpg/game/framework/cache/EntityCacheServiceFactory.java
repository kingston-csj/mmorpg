package org.forfun.mmorpg.game.framework.cache;

import org.forfun.mmorpg.framework.cache.BaseEntityCacheService;

public interface EntityCacheServiceFactory {

    /**
     * 创建实体缓存服务
     *
     * @param entityClass
     * @return
     */
    @SuppressWarnings({"rawtypes"})
    BaseEntityCacheService create(Class entityClass);
}