package com.kingston.mmorpg.game.database.config.container;

import com.kingston.mmorpg.game.database.config.ConfigContainer;
import com.kingston.mmorpg.game.database.config.Reloadable;

public interface ReloadableContainer<K, V> extends Reloadable, ConfigContainer<K, V>{

}
