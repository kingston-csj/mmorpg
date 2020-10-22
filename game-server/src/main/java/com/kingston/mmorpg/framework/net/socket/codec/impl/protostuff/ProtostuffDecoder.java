package com.kingston.mmorpg.framework.net.socket.codec.impl.protostuff;

import com.kingston.mmorpg.framework.net.socket.MessageFactory;
import com.kingston.mmorpg.framework.net.socket.codec.IMessageDecoder;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtostuffDecoder implements IMessageDecoder {

    private static Logger logger = LoggerFactory.getLogger(ProtostuffDecoder.class);

    @Override
    public Message readMessage(short cmd, byte[] body) {
        Class<?> msgClazz = MessageFactory.getInstance().getMessageMeta(cmd);
        try {
            Message message = (Message) ProtostuffCodecUtil.deserializer(body, msgClazz);
            return message;
        } catch (Exception e) {
            logger.error("读取消息出错,模块号{}，异常{}", new Object[]{ cmd ,e});
        }
        return null;
    }
}
