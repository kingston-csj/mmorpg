package org.forfun.mmorpg.protocol.codec.impl.protostuff;

import org.forfun.mmorpg.protocol.codec.IMessageDecoder;
import org.forfun.mmorpg.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtostuffDecoder implements IMessageDecoder {

    private static Logger logger = LoggerFactory.getLogger(ProtostuffDecoder.class);

    @Override
    public Message readMessage(Class<?> msgClazz, byte[] body) {
        try {
            Message message = (Message) ProtostuffCodecUtil.deserializer(body, msgClazz);
            return message;
        } catch (Exception e) {
            logger.error("读取消息出错,模块号{}，异常{}", new Object[]{ msgClazz.getName() ,e});
        }
        return null;
    }
}
