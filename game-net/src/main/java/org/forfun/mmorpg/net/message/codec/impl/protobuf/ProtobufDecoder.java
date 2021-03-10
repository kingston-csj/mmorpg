package org.forfun.mmorpg.net.message.codec.impl.protobuf;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import org.forfun.mmorpg.net.message.codec.IMessageDecoder;
import org.forfun.mmorpg.net.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ProtobufDecoder implements IMessageDecoder {

    private static Logger logger = LoggerFactory.getLogger(ProtobufDecoder.class);

    @Override
    public Message readMessage(Class<?> msgClazz, byte[] body) {
        try {
            Codec<?> codec = ProtobufProxy.create(msgClazz);
            Message message = (Message) codec.decode(body);
            return message;
        } catch (IOException e) {
            logger.error("读取消息出错,模块号{}，异常{}", new Object[]{ msgClazz.getName() ,e});
        }
        return null;
    }

}