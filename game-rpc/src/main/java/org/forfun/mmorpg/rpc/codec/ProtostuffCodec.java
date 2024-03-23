package org.forfun.mmorpg.rpc.codec;

import jforgame.codec.MessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtostuffCodec implements MessageCodec {

    private static final Logger logger = LoggerFactory.getLogger(ProtostuffCodec.class);
    @Override
    public Object decode(Class<?> msgClazz, byte[] body) {
        try {
            return ProtostuffCodecUtil.deserializer(body, msgClazz);
        } catch (Exception e) {
            logger.error("读取消息出错,模块号{}", msgClazz.getName(),e);
        }
        return null;
    }

    @Override
    public byte[] encode(Object message) {
        return ProtostuffCodecUtil.serializer(message);
    }
}
