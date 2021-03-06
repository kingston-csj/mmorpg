package org.forfun.mmorpg.protocol.codec.impl.json;


import org.forfun.mmorpg.protocol.codec.IMessageDecoder;
import org.forfun.mmorpg.protocol.codec.IMessageEncoder;
import org.forfun.mmorpg.protocol.codec.SerializerFactory;

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