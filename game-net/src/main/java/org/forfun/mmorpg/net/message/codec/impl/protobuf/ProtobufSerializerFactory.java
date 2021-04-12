package org.forfun.mmorpg.net.message.codec.impl.protobuf;


import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import org.forfun.mmorpg.net.message.codec.IMessageDecoder;
import org.forfun.mmorpg.net.message.codec.IMessageEncoder;
import org.forfun.mmorpg.net.message.codec.SerializerFactory;

import java.util.Set;

public class ProtobufSerializerFactory implements SerializerFactory {

    private IMessageDecoder decoder = new ProtobufDecoder();

    private IMessageEncoder encoder = new ProtobufEncoder();

    /**
     * 预编译
     * @param clazzPool
     */
    public void preCompile(Set<Class<?>> clazzPool) {
        for (Class<?> clazz : clazzPool) {
            ProtobufProxy.create(clazz);
        }
    }

    @Override
    public IMessageDecoder getDecoder() {
        return decoder;
    }

    @Override
    public IMessageEncoder getEncoder() {
        return encoder;
    }

}