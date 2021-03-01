package com.kingston.mmorpg.net.socket.codec.impl.protobuf;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.kingston.mmorpg.net.socket.MessageFactory;
import com.kingston.mmorpg.net.socket.codec.IMessageDecoder;
import com.kingston.mmorpg.net.socket.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ProtobufDecoder implements IMessageDecoder {

    private static Logger logger = LoggerFactory.getLogger(ProtobufDecoder.class);

    @Override
    public Message readMessage(short cmd, byte[] body) {
        Class<?> msgClazz = MessageFactory.getInstance().getMessageMeta(cmd);
        try {
            Codec<?> codec = ProtobufProxy.create(msgClazz);
            Message message = (Message) codec.decode(body);
            return message;
        } catch (IOException e) {
            logger.error("读取消息出错,模块号{}，异常{}", new Object[]{ cmd ,e});
        }
        return null;
    }

}