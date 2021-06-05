package org.forfun.mmorpg.protocol.codec.impl.protostuff;


import org.forfun.mmorpg.protocol.MessageFactory;
import org.forfun.mmorpg.protocol.codec.IMessageDecoder;
import org.forfun.mmorpg.protocol.codec.IMessageEncoder;
import org.forfun.mmorpg.protocol.codec.Preprocessed;
import org.forfun.mmorpg.protocol.codec.SerializerFactory;

public class ProtostuffSerializerFactory implements SerializerFactory, Preprocessed {

    private IMessageDecoder decoder = new ProtostuffDecoder();

    private IMessageEncoder encoder = new ProtostuffEncoder();

    @Override
    public void preCompile() {
        MessageFactory.getInstance().listAllMessages().forEach(c ->
                ProtostuffCodecUtil.getSchema(c));
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
