package org.forfun.mmorpg.client.net;

import org.forfun.mmorpg.game.logger.LoggerUtils;
import org.forfun.mmorpg.net.message.MessageFactory;
import org.forfun.mmorpg.net.message.codec.IMessageEncoder;
import org.forfun.mmorpg.net.message.Message;
import org.forfun.mmorpg.net.socket.netty.NettyPacketEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientPacketEncoder  extends MessageToByteEncoder<Message> {

    private Logger logger = LoggerFactory.getLogger(NettyPacketEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        try {
            _encode(ctx, msg, out);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
        }
    }

    private void _encode(ChannelHandlerContext ctx, Message message, ByteBuf out) throws Exception {
        // ----------------消息协议格式-------------------------
        // packetLength |cmd   | body
        // short         short    byte[]
        // 其中 packetLength长度占2位，由编码链 LengthFieldPrepender(2) 提供

        short cmd = MessageFactory.getInstance().getMessageId(message.getClass());
        // 写入cmd类型
        out.writeShort(cmd);
        try {
            IMessageEncoder msgEncoder = ClientSerializerHelper.getInstance().getEncoder();
            byte[] body = msgEncoder.writeMessageBody(message);
            out.writeBytes(body);
        } catch (Exception e) {
            LoggerUtils.error("读取消息出错,协议号{}}异常{}", new Object[] {cmd, e });
        }
    }

}