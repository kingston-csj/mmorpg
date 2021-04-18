package org.forfun.mmorpg.net.message.codec.impl.protobuf;


import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import org.forfun.mmorpg.net.message.MessageFactory;
import org.forfun.mmorpg.net.message.codec.IMessageDecoder;
import org.forfun.mmorpg.net.message.codec.IMessageEncoder;
import org.forfun.mmorpg.net.message.codec.Preprocessed;
import org.forfun.mmorpg.net.message.codec.SerializerFactory;

public class ProtobufSerializerFactory implements SerializerFactory, Preprocessed {

    private IMessageDecoder decoder = new ProtobufDecoder();

    private IMessageEncoder encoder = new ProtobufEncoder();

    @Override
    public void preCompile() {
        MessageFactory.getInstance().listAllMessages().forEach(c ->
                ProtobufProxy.create(c));
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