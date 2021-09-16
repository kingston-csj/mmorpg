package org.forfun.mmorpg.rpc.server;

import java.lang.reflect.Method;

public class RpcHandler {

    private Object service;

    private Method method;

    private Class<?>[] parameterTypes;

    public Object getService() {
        return service;
    }

    public void setService(Object service) {
        this.service = service;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}
