package org.forfun.mmorpg.game.util;

import org.forfun.mmorpg.game.base.GameContext;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 全局id生成器
 * 
 *
 */
public class IdFactory {

	private static AtomicLong generator = new AtomicLong(0);

	/**
	 * 生成全局唯一id
	 */
	public static long getNextId() {
		//----------------id格式 -------------------------
		//----------long类型8个字节64个比特位----------------
		// 高16位          	| 中32位          |  低16位
		// serverId        系统秒数          自增长号

		long serverId = (long) GameContext.getServerConfig().getServerId();
		return  (serverId << 48)
				|	(((System.currentTimeMillis()/1000) & 0xFFFFFFFF) << 16)
				| (generator.getAndIncrement() & 0xFFFF);
	}



}
