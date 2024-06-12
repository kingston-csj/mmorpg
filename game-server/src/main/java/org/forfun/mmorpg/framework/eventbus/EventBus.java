package org.forfun.mmorpg.framework.eventbus;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 事件总线 从guava-eventbus精简而来
 */
@Component
public class EventBus {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Executor executor;

    private SubscriberRegistry registry = new SubscriberRegistry();

    private static EventBus self;

    @PostConstruct
    public void init() {
        // 异步执行的需求很少，一条线程就够了
        this.executor = Executors.newSingleThreadExecutor();
        self = this;
    }

    public static EventBus getInstance() {
        return self;
    }

    public void register(Object object) {
        registry.register(object);
    }

    /**
     * 同步处理事件
     *
     * @param event
     */
    public void post(BaseEvent event) {
        Class<? extends BaseEvent> eventType = event.getClass();
        Set<Subscriber> subscribers = registry.getSubscribersForEvent(eventType);

        subscribers.forEach((subscriber) -> {
            try {
                subscriber.handleEvent(event);
            } catch (Exception e) {
                logger.error("", e);
            }
        });
    }

    /**
     * 异步处理事件
     *
     * @param event
     */
    public void asyncPost(BaseEvent event) {
        this.executor.execute(() -> {
            post(event);
        });
    }
}
