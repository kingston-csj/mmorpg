package com.kingston.mmorpg.net.socket.codec.impl.protobuf;


import com.kingston.mmorpg.net.socket.codec.IMessageDecoder;
import com.kingston.mmorpg.net.socket.codec.IMessageEncoder;
import com.kingston.mmorpg.net.socket.codec.SerializerFactory;

public class ProtobufSerializerFactory implements SerializerFactory {

    private IMessageDecoder decoder = new ProtobufDecoder();

    private IMessageEncoder encoder = new ProtobufEncoder();

    @Override
    public IMessageDecoder getDecoder() {
        return decoder;
    }

    @Override
    public IMessageEncoder getEncoder() {
        return encoder;
    }

}