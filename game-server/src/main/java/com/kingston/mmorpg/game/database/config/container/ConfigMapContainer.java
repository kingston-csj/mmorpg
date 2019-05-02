package com.kingston.mmorpg.game.database.config.container;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kingston.mmorpg.game.database.config.domain.ConfigMap;


@Component
public class ConfigMapContainer {

	private Map<Integer, ConfigMap> allMaps = new HashMap<>();

	public Collection<ConfigMap> queryAllMaps() {
		return allMaps.values();
	}

}
