package org.forfun.mmorpg.rpc.server;

import io.netty.channel.Channel;
import org.forfun.mmorpg.protocol.codec.impl.protostuff.ProtostuffCodecUtil;
import org.forfun.mmorpg.rpc.data.RpcDataPackage;
import org.forfun.mmorpg.rpc.data.RpcRequestData;
import org.forfun.mmorpg.rpc.data.RpcResponseData;
import org.forfun.mmorpg.rpc.util.ServiceSignatureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class RpcServiceRegistry {

    private Logger logger = LoggerFactory.getLogger(RpcServiceRegistry.class.getName());

    private Map<String, RpcHandler> handlers = new HashMap<>();

    public void register(Object service) {
        Class clz = service.getClass();
        RpcService rpcService = (RpcService) clz.getAnnotation(RpcService.class);

        Method[] methods = clz.getDeclaredMethods();
        for (Method method : methods) {
            String serviceName = service.getClass().getSimpleName();
            if (rpcService.name() != null && rpcService.name().length() > 0) {
                serviceName = rpcService.name();
            }

            if (Modifier.isPublic(method.getModifiers()) && method.isAnnotationPresent(RpcInvoker.class)) {
                RpcInvoker rpcInvoker = method.getAnnotation(RpcInvoker.class);
                String methodName = method.getName();
                if (rpcInvoker.methodName() != null && rpcInvoker.methodName().length() > 0) {
                    methodName = rpcInvoker.methodName();
                }
                String key = ServiceSignatureUtil.makeSignature(serviceName, methodName);
                if (handlers.containsKey(key)) {
                    // 不支持方法名称重载
                    throw new IllegalStateException("rpc handler " + key + "duplicate");
                }

                RpcHandler handler = new RpcHandler();
                handler.setService(service);
                handler.setParameterTypes(method.getParameterTypes());
                handler.setMethod(method);
                handlers.put(key, handler);
            }
        }
    }

    public void dispatch(Channel channel, RpcDataPackage request) {
        RpcRequestData requestData = ProtostuffCodecUtil.deserializer(request.getData(), RpcRequestData.class);
        RpcHandler handler = handlers.get(requestData.getServiceName());
        RpcResponseData responseData = new RpcResponseData();
        responseData.setIndex(requestData.getIndex());
        if (handler == null) {
            responseData.setErrorText(String.format("invalid rpc service: %s", requestData.getServiceName()));
            RpcDataPackage sentMessage = RpcDataPackage.newResponse(responseData);
            channel.writeAndFlush(sentMessage);
            return;
        }
        try {
            Object response = handler.getMethod().invoke(handler.getService(), requestData.getExtraParams());
            responseData.setResponse(response);
        } catch (Exception e) {
            responseData.setErrorText(e.getMessage());
            e.printStackTrace();
        } finally {
            channel.writeAndFlush(RpcDataPackage.newResponse(responseData));
        }
    }

}
