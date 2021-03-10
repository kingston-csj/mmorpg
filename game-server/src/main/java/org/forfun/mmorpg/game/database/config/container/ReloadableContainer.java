package org.forfun.mmorpg.game.database.config.container;

import org.forfun.mmorpg.game.database.config.ConfigContainer;
import org.forfun.mmorpg.game.database.config.Reloadable;

public interface ReloadableContainer<K, V> extends Reloadable, ConfigContainer<K, V> {

}
