package org.forfun.mmorpg.game.base;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jforgame.commons.thread.NamedThreadFactory;
import org.forfun.mmorpg.game.logger.LoggerUtils;
import org.springframework.stereotype.Component;

@Component
public class SchedulerManager {

    private static SchedulerManager instance;

    private ScheduledExecutorService service;


    public static SchedulerManager getInstance() {
        return instance;
    }

    @PostConstruct
    private void init() {
        service = Executors.newScheduledThreadPool(2,new NamedThreadFactory("common-scheduler"));
        instance = this;
    }

    /**
     * @param command
     * @param initialDelay 毫秒数
     * @param period       毫秒数
     * @return
     */
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command,
                                                  long initialDelay, long period) {
        return service.scheduleAtFixedRate(new LogTask(command), initialDelay, period, TimeUnit.MILLISECONDS);
    }

    /**
     * @param command
     * @param delay   延迟毫秒数
     * @return
     */
    public ScheduledFuture<?> schedule(Runnable command,
                                       long delay) {
        return service.schedule(new LogTask(command), delay, TimeUnit.MILLISECONDS);
    }

    @PreDestroy
    public void shutDown() {
        service.shutdown();
        service.shutdownNow();
        LoggerUtils.error("定时器关闭结束");
    }


    private static class LogTask implements Runnable {

        Runnable wrapper;

        public LogTask(Runnable wrapper) {
            this.wrapper = wrapper;
        }

        @Override
        public void run() {
            try {
                wrapper.run();
            } catch (Exception e) {
                LoggerUtils.error("定时任务执行异常", e);
            }
        }
    }

}
