package org.forfun.mmorpg.mq.consume;

import java.lang.reflect.Method;

public class MqHandlerUnit {

    private Object handler;

    private Method method;

    public Object getHandler() {
        return handler;
    }

    public void setHandler(Object handler) {
        this.handler = handler;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
