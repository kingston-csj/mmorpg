package org.forfun.mmorpg.net.socket.mina;

import org.forfun.mmorpg.net.message.codec.IMessageEncoder;
import org.forfun.mmorpg.net.message.Message;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 *
 */
public class MinaProtocolEncoder implements ProtocolEncoder {

    private IMessageEncoder msgEncoder;

    public MinaProtocolEncoder(IMessageEncoder msgEncoder) {
        this.msgEncoder = msgEncoder;
    }

    @Override
    public void dispose(IoSession arg0) throws Exception {

    }

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        if (message instanceof Message) {
            IoBuffer buffer = writeMessage((Message) message);
            out.write(buffer);
        } else {
            byte[] body = (byte[]) message;
            IoBuffer buffer = IoBuffer.allocate(body.length);
            buffer.setAutoExpand(true);
            buffer.put(body);
        }
    }

    private IoBuffer writeMessage(Message message) {
        // ----------------消息协议格式-------------------------
        // packetLength | cmd      | body
        // int            short      byte[]

        IoBuffer buffer = IoBuffer.allocate(CodecContext.WRITE_CAPACITY);
        buffer.setAutoExpand(true);

        // 写入具体消息的内容
        byte[] body = msgEncoder.writeMessageBody(message);
        // 消息元信息常量, short表示cmd
        final int metaSize = 2;
        // 消息内容长度
        buffer.putInt(body.length + metaSize);
        short cmd = message.getCmd();
        // 写入cmd类型
        buffer.putShort(cmd);

        buffer.put(body);
        // 回到buff字节数组头部
        buffer.flip();

        return buffer;
    }

}
