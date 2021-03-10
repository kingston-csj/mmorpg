package org.forfun.mmorpg.net.socket.mina;

import org.forfun.mmorpg.net.message.MessageFactory;
import org.forfun.mmorpg.net.message.codec.IMessageDecoder;
import org.forfun.mmorpg.net.message.Message;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinaProtocolDecoder extends CumulativeProtocolDecoder {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private IMessageDecoder msgDecoder;

    public MinaProtocolDecoder(IMessageDecoder msgDecoder) {
        this.msgDecoder = msgDecoder;
    }

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        if (in.remaining() < 4) {
            return false;
        }
        in.mark();

        int length = in.getInt();
        int maxReceiveBytes = 4096;
        if (length > maxReceiveBytes) {
            logger.error("单包长度[{}]过大，断开链接", length);
            session.close(true);
            return true;
        }

        if (in.remaining() < length) {
            in.reset();
            return false;
        }

        // 消息元信息常量, short表示cmd
        final int metaSize = 2;
        byte cmd = in.get();
        byte[] body = new byte[length - metaSize];
        in.get(body);
        Class<?> msgClazz = MessageFactory.getInstance().getMessageMeta(cmd);
        out.write(readMessage(msgClazz, body));
        return true;
    }

    private Message readMessage(Class<?> msgClazz, byte[] body) {
        try {
            Message message = msgDecoder.readMessage(msgClazz, body);
            return message;
        } catch (Exception e) {
            logger.error("读取消息出错,协议号{},异常{}", new Object[]{msgClazz.getName(), e});
        }
        return null;
    }

}