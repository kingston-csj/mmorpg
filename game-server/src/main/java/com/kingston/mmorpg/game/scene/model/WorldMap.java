package com.kingston.mmorpg.game.scene.model;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author kingston 世界地图模型
 */
public class WorldMap {

	/** 以该地图为模型的所有场景 */
	private ConcurrentMap<Long, Scene> scenes = new ConcurrentHashMap<>();

	/** 地图模型id */
	private int modelId;

}
