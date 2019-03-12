package com.kingston.mmorpg.game.database.config;

import java.util.Collection;

public interface ConfigContainer<K, V> {

	V queryOne(K id);

	Collection<V> queryAll();

}
