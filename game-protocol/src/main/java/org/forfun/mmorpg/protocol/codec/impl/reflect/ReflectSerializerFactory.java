package org.forfun.mmorpg.protocol.codec.impl.reflect;


import org.forfun.mmorpg.protocol.codec.IMessageDecoder;
import org.forfun.mmorpg.protocol.codec.IMessageEncoder;
import org.forfun.mmorpg.protocol.codec.SerializerFactory;

public class ReflectSerializerFactory implements SerializerFactory {

    private IMessageDecoder decoder = new ReflectDecoder();

    private IMessageEncoder encoder = new ReflectEncoder();

    @Override
    public IMessageDecoder getDecoder() {
        return decoder;
    }

    @Override
    public IMessageEncoder getEncoder() {
        return encoder;
    }

}