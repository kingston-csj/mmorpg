package com.kingston.mmorpg.framework.net.socket.codec.impl.protobuf;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.kingston.mmorpg.framework.net.socket.codec.IMessageEncoder;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ProtobufEncoder implements IMessageEncoder {

    private static Logger logger = LoggerFactory.getLogger(ProtobufEncoder.class);

    @Override
    public byte[] writeMessageBody(Message message) {
        //写入具体消息的内容
        byte[] body = null;
        Class<Message> msgClazz = (Class<Message>) message.getClass();
        try {
            Codec<Message> codec = ProtobufProxy.create(msgClazz);
            body = codec.encode(message);
        } catch (IOException e) {
            logger.error("", e);
        }
        return body;
    }

}
