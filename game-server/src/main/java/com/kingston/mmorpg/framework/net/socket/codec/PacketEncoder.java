package com.kingston.mmorpg.framework.net.socket.codec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kingston.mmorpg.framework.net.socket.MessageFactory;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.framework.net.socket.serializer.Serializer;
import com.kingston.mmorpg.game.logs.LoggerUtils;

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
		// packetLength | moduleId | cmd | body
		// short short short byte[]
		// 其中 packetLength长度占2位，由编码链 LengthFieldPrepender(2) 提供

		short module = message.getModule();
		short cmd = message.getCmd();
		// 写入module类型
		out.writeShort(module);
		// 写入cmd类型
		out.writeShort(cmd);
		Class<?> msgClazz = MessageFactory.getInstance().getMessageMeta(module, cmd);
		try {
			Serializer messageCodec = Serializer.getSerializer(msgClazz);
			messageCodec.encode(out, message);
		} catch (Exception e) {
			LoggerUtils.error("读取消息出错,模块号{}，类型{},异常{}", new Object[] { module, cmd, e });
		}
	}

}
