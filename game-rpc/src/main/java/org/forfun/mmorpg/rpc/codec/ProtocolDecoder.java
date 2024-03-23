package org.forfun.mmorpg.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.forfun.mmorpg.rpc.data.RpcDataPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProtocolDecoder extends ByteToMessageDecoder {

    private int maxReceiveBytes = 4096;

    private final Logger logger = LoggerFactory.getLogger(ProtocolDecoder.class);


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
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

        ByteBuf body = in.readBytes(length);

        byte[] extraParam = new byte[length];
        body.readBytes(extraParam);

        RpcDataPackage request = ProtostuffCodecUtil.deserializer(extraParam, RpcDataPackage.class);

        out.add(request);
    }
}
