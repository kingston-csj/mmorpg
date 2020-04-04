package com.kingston.mmorpg.framework.net.socket.codec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kingston.mmorpg.framework.net.socket.MessageFactory;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.framework.net.socket.serializer.Serializer;
import com.kingston.mmorpg.game.logger.LoggerUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Message> {

	private Logger logger = LoggerFactory.getLogger(PacketEncoder.class);

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
			Serializer messageCodec = Serializer.getSerializer(message.getClass());
			messageCodec.encode(out, message);
		} catch (Exception e) {
			LoggerUtils.error("读取消息出错,协议号{}}异常{}", new Object[] {cmd, e });
		}
	}

}
