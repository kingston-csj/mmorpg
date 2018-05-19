package com.kingston.mmorpg.game.database.config.container;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.kingston.mmorpg.game.database.config.bean.ConfigMap;

public class ConfigMapContainer {
	
	private Map<Integer, ConfigMap> allMaps = new HashMap<>();
	
	public Collection<ConfigMap> queryAllMaps() {
		return allMaps.values();
	}

}
