package com.kingston.mmorpg.net.socket.codec.impl.json;

import com.kingston.mmorpg.net.socket.MessageFactory;
import com.kingston.mmorpg.net.socket.codec.IMessageDecoder;
import com.kingston.mmorpg.net.socket.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonDecoder implements IMessageDecoder {

    private static Logger logger = LoggerFactory.getLogger(JsonDecoder.class);

    @Override
    public Message readMessage(short cmd, byte[] body) {
        Class<?> msgClazz = MessageFactory.getInstance().getMessageMeta(cmd);
        try {
            String content =  new String(body,"UTF-8");
            Message message = (Message) JsonUtil.string2Object(content, msgClazz);
            return message;
        } catch (IOException e) {
            logger.error("读取消息出错,模块号{}，异常{}", new Object[]{ cmd ,e});
        }
        return null;
    }

}