package org.forfun.mmorpg.net.socket.netty;

import org.forfun.mmorpg.net.message.MessageFactory;
import org.forfun.mmorpg.net.message.codec.IMessageDecoder;
import org.forfun.mmorpg.net.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyPacketDecoder extends LengthFieldBasedFrameDecoder {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private IMessageDecoder msgDecoder;

	public NettyPacketDecoder(IMessageDecoder msgDecoder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
							  int initialBytesToStrip) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
		this.msgDecoder = msgDecoder;
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
			byte[] body = new byte[in.readableBytes()];
			in.readBytes(body);
			Class<?> msgClazz = MessageFactory.getInstance().getMessageMeta(cmd);
			Message msg = msgDecoder.readMessage(msgClazz, body);
			return msg;
		} catch (Exception e) {
			logger.error("读取消息出错,协议号{},异常{}", new Object[] {cmd, e });
			// 消息不往下传，手动释放下引用
			ReferenceCountUtil.release(in);
		}
		return null;
	}

}
