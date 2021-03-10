package org.forfun.mmorpg.game.asyncdb;

import org.forfun.mmorpg.common.util.thread.NamedThreadFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class EntitySaveThreadExecutor {

    private ScheduledExecutorService executorService;

    @PostConstruct
    private void init() {
        Executors.newScheduledThreadPool(1, new NamedThreadFactory("db-delay-executor"));
    }

    public void addDelayTask(Runnable task) {

    }
}
