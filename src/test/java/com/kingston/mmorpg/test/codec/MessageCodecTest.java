package com.kingston.mmorpg.test.codec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.framework.net.socket.serializer.Serializer;
import com.kingston.mmorpg.game.base.SpringContext;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class MessageCodecTest {

	private static ConfigurableApplicationContext context;
	
	@BeforeClass
	public static void init() {
		context = new FileSystemXmlApplicationContext("config/applicationContext.xml");
	}
	
	@Test
	public void test() throws Exception {
		ReqSelectePlayer oldMsg = new ReqSelectePlayer();
		oldMsg.setPlayerId(123L);
		oldMsg.setName("sdf");
		List<Long> ids = Arrays.asList(1L,2L,3L);
		oldMsg.setIds(ids);
		
		List<PlayerVo> vos = new ArrayList<>();
		vos.add(new PlayerVo(11,"hello"));
		vos.add(new PlayerVo(22,"world"));
		oldMsg.setVos(vos);
		
		ByteBuf buf =  Unpooled.buffer();
		
		SpringContext.getMessageFactory().writeMessage(buf, oldMsg);
		
		MessageMeta annotation = (MessageMeta)oldMsg.getClass().getAnnotation(MessageMeta.class);
		short module = annotation.module();
		short cmd = annotation.cmd();
		buf.readShort();
		buf.readShort();
		
		Class<?> clazz = SpringContext.getMessageFactory().getMessageMeta(module, cmd);
		
		Serializer serializer = Serializer.getSerializer(clazz);
		
		Object newMsg = serializer.decode(buf, clazz, null);
		System.err.println(newMsg);
		Assert.assertTrue(oldMsg.equals(newMsg));
	}
	
}
