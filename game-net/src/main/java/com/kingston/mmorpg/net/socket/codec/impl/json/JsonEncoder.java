package com.kingston.mmorpg.net.socket.codec.impl.json;

import com.kingston.mmorpg.net.socket.codec.IMessageEncoder;
import com.kingston.mmorpg.net.socket.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonEncoder implements IMessageEncoder {

    private static Logger logger = LoggerFactory.getLogger(JsonEncoder.class);

    @Override
    public byte[] writeMessageBody(Message message) {
        try {
            String content = JsonUtil.object2String(message);
            byte[] body = content.getBytes();
            return body;
        } catch (Exception e) {
            logger.error("读取消息出错", e);
        }
        return new byte[0];
    }
}
