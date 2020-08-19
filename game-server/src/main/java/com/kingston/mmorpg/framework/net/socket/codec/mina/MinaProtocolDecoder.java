package com.kingston.mmorpg.framework.net.socket.codec.mina;

import com.kingston.mmorpg.framework.net.socket.codec.SerializerHelper;
import com.kingston.mmorpg.framework.net.socket.codec.IMessageDecoder;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.game.logger.LoggerUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinaProtocolDecoder extends CumulativeProtocolDecoder {

    private Logger logger = LoggerFactory.getLogger(MinaProtocolDecoder.class);

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

        // 消息元信息常量3表示消息body前面的两个字段，一个short表示module，一个byte表示cmd,
        final int metaSize = 3;
        byte cmd = in.get();
        byte[] body = new byte[length - metaSize];
        in.get(body);
        out.write(readMessage(cmd, body));
        return true;
    }

    private Message readMessage(short cmd, byte[] body) {
        try {
            IMessageDecoder msgDecoder = SerializerHelper.getInstance().getDecoder();
            Message message = msgDecoder.readMessage(cmd, body);
            return message;
        } catch (Exception e) {
            LoggerUtils.error("读取消息出错,协议号{},异常{}", new Object[]{cmd, e});
        }
        return null;
    }

}