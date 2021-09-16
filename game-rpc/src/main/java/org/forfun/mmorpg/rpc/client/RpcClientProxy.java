package org.forfun.mmorpg.rpc.client;

import io.netty.channel.Channel;
import org.forfun.mmorpg.rpc.data.RpcDataPackage;
import org.forfun.mmorpg.rpc.data.RpcRequestData;
import org.forfun.mmorpg.rpc.util.ServiceSignatureUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicLong;

public class RpcClientProxy<T> implements InvocationHandler {

    private RpcClient rpcClient;

    private Channel session;

    private AtomicLong idFactory = new AtomicLong();

    private T instance;

    private Class<T> instanceClazz;

    public RpcClientProxy(RpcClient rpcClient, Class<T> clazz) throws Exception {
        this.session= rpcClient.createSession();
        this.instanceClazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String serviceName = instanceClazz.getSimpleName();
        String methodName = method.getName();
        String serviceSignature = ServiceSignatureUtil.makeSignature(serviceName, methodName);

        long index = idFactory.getAndIncrement();

        RpcRequestData request = new RpcRequestData();
        request.setIndex(index);
        request.setServiceName(serviceSignature);
        request.setExtraParams(args);

        RpcDataPackage rpcData = RpcDataPackage.newRequest(request);

        int timeout = 5000;

        final RequestResponseFuture future = new RequestResponseFuture(index,  timeout,null);
        try {
            CallBackService.getInstance().register(index, future);
            session.writeAndFlush(rpcData);
            Object responseMessage = future.waitResponseMessage(timeout);
            if (responseMessage == null) {
                CallTimeoutException exception = new CallTimeoutException("send request message  failed");
                future.setCause(exception);
                throw exception;
            } else {
                return responseMessage;
            }
        } catch (InterruptedException e) {
            future.setCause(e);
            throw e;
        } finally {
            CallBackService.getInstance().remove(index);
        }
    }

    public T proxy() {
        if (instance != null) {
            return instance;
        }
        instance =  (T) Proxy.newProxyInstance(instanceClazz.getClassLoader(), new Class[]{instanceClazz}, this);
        return instance;
    }

}
