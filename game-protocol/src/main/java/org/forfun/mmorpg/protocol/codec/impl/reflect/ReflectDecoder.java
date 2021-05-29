package org.forfun.mmorpg.protocol.codec.impl.reflect;

import org.forfun.mmorpg.protocol.codec.IMessageDecoder;
import org.forfun.mmorpg.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class ReflectDecoder implements IMessageDecoder {

    private static Logger logger = LoggerFactory.getLogger(ReflectDecoder.class);

    @Override
    public Message readMessage(Class<?> msgClazz, byte[] body) {
        // 消息序列化这里的buff已经是一个完整的包体
        ByteBuffer in = ByteBuffer.allocate(body.length);
        in.put(body);
        in.flip();

        try {
            Codec messageCodec = Codec.getSerializer(msgClazz);
            Message message = (Message) messageCodec.decode(in, msgClazz, null);
            return message;
        } catch (Exception e) {
            logger.error("读取消息出错,模块号{}，异常{}", new Object[]{msgClazz.getName(), e});
        }
        return null;
    }

}