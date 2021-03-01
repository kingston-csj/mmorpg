package com.kingston.mmorpg.net.socket.codec.impl.protostuff;


import com.kingston.mmorpg.net.socket.codec.IMessageDecoder;
import com.kingston.mmorpg.net.socket.codec.IMessageEncoder;
import com.kingston.mmorpg.net.socket.codec.SerializerFactory;

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
