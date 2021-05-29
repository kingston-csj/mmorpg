package org.forfun.mmorpg.net.socket.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.forfun.mmorpg.protocol.Message;
import org.forfun.mmorpg.protocol.MessageFactory;
import org.forfun.mmorpg.protocol.codec.IMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NettyPacketDecoder extends ByteToMessageDecoder {

    private int maxReceiveBytes = 4096;

    private Logger logger = LoggerFactory.getLogger(NettyPacketDecoder.class);

    private IMessageDecoder msgDecoder;

    public NettyPacketDecoder(IMessageDecoder msgDecoder) {
        this.msgDecoder = msgDecoder;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        // ----------------消息协议格式-------------------------
        // packetLength |  cmd | body
        // int short  byte[]
        int length = in.readInt();
        if (length > maxReceiveBytes) {
            logger.error("单包长度[{}]过大，断开链接", length);
            ctx.close();
            return;
        }

        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }
        // 消息元信息常量, short表示cmd
        final int metaSize = 2;
        short cmd = in.readShort();
        byte[] body = new byte[length - metaSize];
        in.readBytes(body);
        Class<?> clazz = MessageFactory.getInstance().getMessageMeta(cmd);
        Message msg = msgDecoder.readMessage(clazz, body);

        out.add(msg);
    }

}

