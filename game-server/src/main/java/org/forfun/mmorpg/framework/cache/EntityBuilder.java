package org.forfun.mmorpg.framework.cache;

/**
 * 实体初始化器
 * @param <E>
 */
@FunctionalInterface
public interface EntityBuilder<E> {


    /**
     * 构建一个新的实体，由业务自行初始化
     * @return
     */
    E build();

}
