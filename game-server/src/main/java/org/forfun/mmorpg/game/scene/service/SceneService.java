package org.forfun.mmorpg.game.scene.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.forfun.mmorpg.game.database.config.container.ConfigMapContainer;
import org.forfun.mmorpg.game.database.config.domain.ConfigMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.forfun.mmorpg.game.scene.model.Scene;
import org.forfun.mmorpg.game.scene.model.WorldMap;


@Service
public class SceneService {

	@Autowired
	private ConfigMapContainer configMapContainer;

	private ConcurrentMap<Integer, WorldMap> allMaps = new ConcurrentHashMap<>();

	/**
	 * 初始化所有普通场景
	 */
	public void initNormalScenes() {
		configMapContainer.queryAllMaps().stream().filter(map -> map.getMapType() == 0).forEach(this::initNormalScenes);
	}

	private void initNormalScenes(ConfigMap configMap) {
		WorldMap worldMap = new WorldMap();
		allMaps.put(configMap.getId(), worldMap);
		for (int i = 0; i < configMap.getCoreLine(); i++) {
			Scene scene = new Scene(worldMap);
			scene.init();
			worldMap.addScene(scene.getId(), scene);
		}
	}

}
