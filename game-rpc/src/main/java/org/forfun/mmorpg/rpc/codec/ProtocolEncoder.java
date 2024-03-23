package org.forfun.mmorpg.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.forfun.mmorpg.rpc.data.RpcDataPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProtocolEncoder extends MessageToByteEncoder<Object> {

    private final Logger logger = LoggerFactory.getLogger(ProtocolEncoder.class);

    private static ThreadLocal<ByteBuf> localBuf = ThreadLocal.withInitial(() -> Unpooled.buffer(10240));


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        try {
            _encode(ctx, msg, out);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
        }
    }

    private void _encode(ChannelHandlerContext ctx, Object message, ByteBuf out) throws Exception {
        RpcDataPackage request = (RpcDataPackage)message;

        ByteBuf buffer = localBuf.get();
        buffer.clear();

        buffer.writeBytes(ProtostuffCodecUtil.serializer(request));

        byte[] body = new byte[buffer.readableBytes()];
        buffer.readBytes(body);

        out.writeInt(body.length);
        out.writeBytes(body);
    }
}