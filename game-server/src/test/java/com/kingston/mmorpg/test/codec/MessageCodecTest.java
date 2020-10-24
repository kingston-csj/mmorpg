package com.kingston.mmorpg.test.codec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kingston.mmorpg.game.ConfigScanPaths;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kingston.mmorpg.framework.net.socket.MessageFactory;
import com.kingston.mmorpg.framework.net.socket.codec.impl.reflect.Codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class MessageCodecTest {

	@BeforeClass
	public static void init() {
		// 初始化协议表
		MessageFactory.getInstance().init(ConfigScanPaths.MESSAGE_BASE_PATH);
	}

	@Test
	public void test() throws Exception {
		ReqSelectePlayer oldMsg = new ReqSelectePlayer();
		oldMsg.setPlayerId(123L);
		oldMsg.setName("sdf");
		List<Long> ids = Arrays.asList(1L, 2L, 3L);
		oldMsg.setIds(ids);
		
		long[] money = new long[] {199, 200};
		oldMsg.setMoney(money);

		List<BaseVo> vos = new ArrayList<>();
		vos.add(new PlayerVo(11, "hello"));
		vos.add(new MonsterVo(220));
		oldMsg.setVos(vos);

		ByteBuf buf = Unpooled.buffer();

//		MessageFactory.getInstance().writeMessage(buf, oldMsg);

		short cmd = MessageFactory.getInstance().getMessageId(oldMsg.getClass());
		buf.readShort();

		Class<?> clazz = MessageFactory.getInstance().getMessageMeta(cmd);

		Codec codec = Codec.getSerializer(ReqSelectePlayer.class);

	}

}
