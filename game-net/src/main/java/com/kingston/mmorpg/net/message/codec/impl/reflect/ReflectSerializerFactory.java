package com.kingston.mmorpg.net.message.codec.impl.reflect;

import com.kingston.mmorpg.net.message.codec.IMessageDecoder;
import com.kingston.mmorpg.net.message.codec.IMessageEncoder;
import com.kingston.mmorpg.net.message.codec.SerializerFactory;

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