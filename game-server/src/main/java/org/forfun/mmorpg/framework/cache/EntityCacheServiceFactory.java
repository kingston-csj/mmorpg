package org.forfun.mmorpg.framework.cache;

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