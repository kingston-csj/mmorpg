package org.forfun.mmorpg.net.message.codec.impl.protostuff;


import org.forfun.mmorpg.net.message.MessageFactory;
import org.forfun.mmorpg.net.message.codec.IMessageDecoder;
import org.forfun.mmorpg.net.message.codec.IMessageEncoder;
import org.forfun.mmorpg.net.message.codec.Preprocessed;
import org.forfun.mmorpg.net.message.codec.SerializerFactory;

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
