package com.kingston.mmorpg.framework.net.socket.serializer;

import com.kingston.mmorpg.framework.net.socket.codec.*;

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