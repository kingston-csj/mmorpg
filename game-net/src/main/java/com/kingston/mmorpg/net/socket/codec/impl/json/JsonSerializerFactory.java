package com.kingston.mmorpg.net.socket.codec.impl.json;

import com.kingston.mmorpg.net.socket.codec.IMessageDecoder;
import com.kingston.mmorpg.net.socket.codec.IMessageEncoder;
import com.kingston.mmorpg.net.socket.codec.SerializerFactory;

public class JsonSerializerFactory implements SerializerFactory {

    private IMessageDecoder decoder = new JsonDecoder();

    private IMessageEncoder encoder = new JsonEncoder();

    @Override
    public IMessageDecoder getDecoder() {
        return decoder;
    }

    @Override
    public IMessageEncoder getEncoder() {
        return encoder;
    }

}