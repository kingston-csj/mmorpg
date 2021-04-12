package org.forfun.mmorpg.net.message.codec.impl.protostuff;


import org.forfun.mmorpg.net.message.codec.IMessageDecoder;
import org.forfun.mmorpg.net.message.codec.IMessageEncoder;
import org.forfun.mmorpg.net.message.codec.SerializerFactory;

import java.util.Set;

public class ProtostuffSerializerFactory implements SerializerFactory {

    private IMessageDecoder decoder = new ProtostuffDecoder();

    private IMessageEncoder encoder = new ProtostuffEncoder();

    /**
     * 预编译
     * @param clazzPool
     */
    public void preCompile(Set<Class<?>> clazzPool) {
        for (Class<?> clazz : clazzPool) {
            ProtostuffCodecUtil.getSchema(clazz);
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
