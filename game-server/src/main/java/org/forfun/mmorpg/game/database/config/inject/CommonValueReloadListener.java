package org.forfun.mmorpg.game.database.config.inject;

/**
 * 当Service本身对{@link CommonValueInject}注解的值进行二次解析，例如string转成map后独立存储，
 * 需要触发reload
 */
public interface CommonValueReloadListener {

    /**
     * 监听配置发生变化
     */
    void afterReload();
}
