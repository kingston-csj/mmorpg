package org.forfun.mmorpg.net.socket.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.forfun.mmorpg.net.message.Message;
import org.forfun.mmorpg.net.message.MessageFactory;
import org.forfun.mmorpg.net.message.codec.IMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class NettyPacketEncoder extends MessageToByteEncoder<Message> {

	private Logger logger = LoggerFactory.getLogger(NettyPacketEncoder.class);

	private IMessageEncoder msgEncoder;

	public NettyPacketEncoder(IMessageEncoder msgEncoder) {
		this.msgEncoder = msgEncoder;
	}

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
		// 其中 packetLength长度占2位

		short cmd = MessageFactory.getInstance().getMessageId(message.getClass());

		try {
			byte[] body = msgEncoder.writeMessageBody(message);
			out.writeInt(2 + body.length);
			// 写入cmd类型
			out.writeShort(cmd);
			out.writeBytes(body);
		} catch (Exception e) {
			logger.error("读取消息出错,协议号{}}异常{}", new Object[] {cmd, e });
		}
	}

}
