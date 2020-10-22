package com.kingston.mmorpg.framework.net.socket.netty;

import com.kingston.mmorpg.framework.net.socket.codec.SerializerHelper;
import com.kingston.mmorpg.framework.net.socket.codec.IMessageDecoder;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.game.logger.LoggerUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.ReferenceCountUtil;

public class NettyPacketDecoder extends LengthFieldBasedFrameDecoder {

	public NettyPacketDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
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

		return readMessage(cmd, frame);
	}

	private Message readMessage(short cmd, ByteBuf in) {
		try {
			IMessageDecoder msgDecoder = SerializerHelper.getInstance().getDecoder();

			byte[] body = new byte[in.readableBytes()];
			in.readBytes(body);
			Message msg = msgDecoder.readMessage(cmd, body);
			return msg;
		} catch (Exception e) {
			LoggerUtils.error("读取消息出错,协议号{},异常{}", new Object[] {cmd, e });
			// 消息不往下传，手动释放下引用
			ReferenceCountUtil.release(in);
		}
		return null;
	}

}
