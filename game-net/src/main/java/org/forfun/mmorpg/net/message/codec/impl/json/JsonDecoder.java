package org.forfun.mmorpg.net.message.codec.impl.json;

import org.forfun.mmorpg.net.message.codec.IMessageDecoder;
import org.forfun.mmorpg.net.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonDecoder implements IMessageDecoder {

    private static Logger logger = LoggerFactory.getLogger(JsonDecoder.class);

    @Override
    public Message readMessage(Class<?> msgClazz, byte[] body) {
        try {
            String content =  new String(body,"UTF-8");
            Message message = (Message) JsonUtil.string2Object(content, msgClazz);
            return message;
        } catch (IOException e) {
            logger.error("读取消息出错,模块号{}，异常{}", new Object[]{ msgClazz.getName() ,e});
        }
        return null;
    }

}