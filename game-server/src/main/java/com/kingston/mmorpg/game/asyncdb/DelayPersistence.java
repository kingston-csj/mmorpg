package com.kingston.mmorpg.game.asyncdb;

import java.util.concurrent.TimeUnit;

/**
 * 延迟持久化
 */
public interface DelayPersistence {

    /**
     * 延迟入库时间（默认为两分钟）
     * @return
     */
    default long delayMillSeconds() {
        return TimeUnit.MINUTES.toMillis(2);
    }
}
