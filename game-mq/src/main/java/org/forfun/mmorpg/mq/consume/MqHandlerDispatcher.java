package org.forfun.mmorpg.mq.consume;

import org.forfun.mmorpg.mq.MqMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MqHandlerDispatcher {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final Map<String, MqHandlerUnit> HANDLERS = new HashMap<>();

    public void registerMethodInvoke(String key, MqHandlerUnit executor) {
        if (HANDLERS.containsKey(key)) {
            throw new RuntimeException(String.format("module[%s] duplicated", key));
        }
        HANDLERS.put(key, executor);
    }

    public void dispatch(MqMessage message) {
        MqHandlerUnit handler = HANDLERS.get(message.getClass().getSimpleName());
        if (handler == null) {
            throw new IllegalArgumentException(message.getClass().getSimpleName() + " not exist");
        }
        try {
            handler.getMethod().invoke(handler.getHandler(), message);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

}
