package com.kingston.mmorpg.framework.net.socket.codec.mina;

import com.kingston.mmorpg.framework.net.socket.codec.IMessageEncoder;
import com.kingston.mmorpg.framework.net.socket.codec.SerializerHelper;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * @author kingston
 */
public class MinaProtocolEncoder implements ProtocolEncoder {

    @Override
    public void dispose(IoSession arg0) throws Exception {

    }

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        IoBuffer buffer = writeMessage((Message) message);
        out.write(buffer);
    }

    private IoBuffer writeMessage(Message message) {
        // ----------------消息协议格式-------------------------
        // packetLength | moduleId | cmd  | body
        // int             short     byte  byte[]

        IoBuffer buffer = IoBuffer.allocate(CodecContext.WRITE_CAPACITY);
        buffer.setAutoExpand(true);

        // 写入具体消息的内容
        IMessageEncoder msgEncoder = SerializerHelper.getInstance().getEncoder();
        byte[] body = msgEncoder.writeMessageBody(message);
        // 消息元信息常量3表示消息body前面的两个字段，一个short表示module，一个byte表示cmd,
        final int metaSize = 3;
        // 消息内容长度
        buffer.putInt(body.length + metaSize);
        short cmd = message.getCmd();
        // 写入cmd类型
        buffer.putShort(cmd);

        buffer.put(body);
//		// 回到buff字节数组头部
        buffer.flip();

        return buffer;
    }

}
