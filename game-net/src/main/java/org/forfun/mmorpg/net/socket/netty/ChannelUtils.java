//package org.forfun.mmorpg.net.socket.netty;
//
//import org.forfun.mmorpg.net.socket.IdSession;
//import io.netty.channel.Channel;
//import io.netty.util.Attribute;
//import io.netty.util.AttributeKey;
//
//import java.net.InetSocketAddress;
//
///**
// * channel的工具类
// *
// */
//public final class ChannelUtils {
//
//	public static AttributeKey<NettySession> SESSION_KEY = AttributeKey.valueOf("session");
//
//	/**
//	 * 添加新的会话
//	 *
//	 * @param channel
//	 * @param session
//	 * @return
//	 */
//	public static boolean addChannelSession(Channel channel, NettySession session) {
//		Attribute<NettySession> sessionAttr = channel.attr(SESSION_KEY);
//		return sessionAttr.compareAndSet(null, session);
//	}
//
//	public static IdSession getSessionBy(Channel channel) {
//		Attribute<NettySession> sessionAttr = channel.attr(SESSION_KEY);
//		return sessionAttr.get();
//	}
//
//
//	public static String getIp(Channel channel) {
//		return ((InetSocketAddress) channel.remoteAddress()).getAddress().toString().substring(1);
//	}
//
//}
