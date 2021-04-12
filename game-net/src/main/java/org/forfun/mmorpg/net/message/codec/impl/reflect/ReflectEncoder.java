package org.forfun.mmorpg.net.message.codec.impl.reflect;

import org.forfun.mmorpg.net.message.Message;
import org.forfun.mmorpg.net.message.codec.IMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class ReflectEncoder implements IMessageEncoder {

    private static Logger logger = LoggerFactory.getLogger(ReflectEncoder.class);

    private static ThreadLocal<ByteBuffer> localBuf = ThreadLocal.withInitial(() -> {
        return ByteBuffer.allocate(10240);
    });

    @Override
    public byte[] writeMessageBody(Message message) {
        ByteBuffer buffer = localBuf.get();
        buffer.clear();
        //写入具体消息的内容
        try {
            Codec messageCodec = Codec.getSerializer(message.getClass());
            messageCodec.encode(buffer, message, null);
        } catch (Exception e) {
            logger.error("读取消息出错,模块号{}, 异常{}",
                    new Object[]{message.getCmd() ,e});
        }
        buffer.flip();
        byte[] body = new byte[buffer.remaining()];
        buffer.get(body);
        return body;
    }

}