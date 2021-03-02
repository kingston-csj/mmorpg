package com.kingston.mmorpg.client.net;

import com.kingston.mmorpg.game.logger.LoggerUtils;
import com.kingston.mmorpg.net.message.MessageFactory;
import com.kingston.mmorpg.net.message.codec.IMessageDecoder;
import com.kingston.mmorpg.net.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.ReferenceCountUtil;

public class ClientPacketDecoder extends LengthFieldBasedFrameDecoder {

    public ClientPacketDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
                               int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }

        // ----------------消息协议格式-------------------------
        // packetLength | cmd   | body
        // short          short    byte  []
        // 其中 packetLength长度占2位，被上层父类LengthFieldBasedFrameDecoder消费了
        // @see new PacketDecoder(1024 * 10, 0, 2, 0, 2)
        if (frame.readableBytes() < 2)
            return null;

        short cmd = frame.readShort();
        Class<?> msgClazz = MessageFactory.getInstance().getMessageMeta(cmd);
        return readMessage(msgClazz, frame);
    }

    private Message readMessage(Class<?> msgClazz, ByteBuf in) {
        try {
            IMessageDecoder msgDecoder = ClientSerializerHelper.getInstance().getDecoder();

            byte[] body = new byte[in.readableBytes()];
            in.readBytes(body);
            Message msg = msgDecoder.readMessage(msgClazz, body);
            return msg;
        } catch (Exception e) {
            LoggerUtils.error("读取消息出错,协议号{},异常{}", new Object[] {msgClazz.getName(), e });
            // 消息不往下传，手动释放下引用
            ReferenceCountUtil.release(in);
        }
        return null;
    }

}