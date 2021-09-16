package org.forfun.mmorpg.rpc.server;

import io.netty.channel.Channel;
import org.forfun.mmorpg.protocol.codec.impl.protostuff.ProtostuffCodecUtil;
import org.forfun.mmorpg.rpc.data.RpcDataPackage;
import org.forfun.mmorpg.rpc.data.RpcRequestData;
import org.forfun.mmorpg.rpc.data.RpcResponseData;
import org.forfun.mmorpg.rpc.util.ServiceSignatureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

@Component
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
            String key = ServiceSignatureUtil.makeSignature(serviceName, method.getName());
            if (handlers.containsKey(key)) {
                // 不支持方法名称重载
                throw new IllegalStateException("rpc handler " + key + "duplicate");
            }
            if (Modifier.isPublic(method.getModifiers())) {
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
        if (handler != null) {
            try {
                Object response = handler.getMethod().invoke(handler.getService(), requestData.getExtraParams());
                RpcResponseData responseData = new RpcResponseData();
                responseData.setResponse(response);
                responseData.setIndex(requestData.getIndex());
                RpcDataPackage sentMessage = RpcDataPackage.newResponse(responseData);
                channel.writeAndFlush(sentMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
