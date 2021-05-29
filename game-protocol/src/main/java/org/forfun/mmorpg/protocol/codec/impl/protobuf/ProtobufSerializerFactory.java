package org.forfun.mmorpg.protocol.codec.impl.protobuf;


import org.forfun.mmorpg.protocol.codec.IMessageDecoder;
import org.forfun.mmorpg.protocol.codec.IMessageEncoder;
import org.forfun.mmorpg.protocol.codec.Preprocessed;
import org.forfun.mmorpg.protocol.codec.SerializerFactory;

public class ProtobufSerializerFactory implements SerializerFactory, Preprocessed {

    private IMessageDecoder decoder = new ProtobufDecoder();

    private IMessageEncoder encoder = new ProtobufEncoder();

    @Override
    public void preCompile() {
//        MessageFactory.getInstance().listAllMessages().forEach(c ->
//                ProtobufProxy.create(c));
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