package org.forfun.mmorpg.net.message.codec.impl.protostuff;


import org.forfun.mmorpg.net.message.codec.IMessageDecoder;
import org.forfun.mmorpg.net.message.codec.IMessageEncoder;
import org.forfun.mmorpg.net.message.codec.SerializerFactory;

public class ProtostuffSerializerFactory implements SerializerFactory {

    private IMessageDecoder decoder = new ProtostuffDecoder();

    private IMessageEncoder encoder = new ProtostuffEncoder();

    @Override
    public IMessageDecoder getDecoder() {
        return decoder;
    }

    @Override
    public IMessageEncoder getEncoder() {
        return encoder;
    }
}
