package org.forfun.mmorpg.game.database.config;

import java.util.Collection;

public interface ConfigContainer<K, V> {

	V queryOne(K id);

	Collection<V> queryAll();

	/**
	 * 配置数据自检
	 */
	void selfChecking();

}
