package com.kingston.mmorpg.framework.net.socket.codec;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.framework.net.socket.serializer.Serializer;
import com.kingston.mmorpg.game.base.SpringContext;
import com.kingston.mmorpg.game.logs.LoggerUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.ReferenceCountUtil;

public class PacketDecoder extends LengthFieldBasedFrameDecoder {

	public PacketDecoder(int maxFrameLength, int lengthFieldOffset,
						 int lengthFieldLength, int lengthAdjustment,
						 int initialBytesToStrip) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment,
				initialBytesToStrip);
	}

	public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		ByteBuf frame = (ByteBuf) super.decode(ctx, in);
		if(frame.readableBytes() <= 0) return null ;
		
		short module = frame.readShort();
		short cmd = frame.readShort();

		return readMessage(module, cmd, frame);
	}
	
	private Message readMessage(short module, short cmd, ByteBuf in) {
		Class<?> msgClazz = SpringContext.getMessageFactory().getMessageMeta(module, cmd);
		try {
			Serializer messageCodec = Serializer.getSerializer(msgClazz);
			Message message = (Message) messageCodec.decode(in, msgClazz, null);
			return message;
		} catch (Exception e) {
			LoggerUtils.error("读取消息出错,模块号{}，类型{},异常{}", new Object[]{module, cmd ,e});
			// 消息不往下传，手动释放下引用
			ReferenceCountUtil.release(in);
		}
		return null;
	}

}
