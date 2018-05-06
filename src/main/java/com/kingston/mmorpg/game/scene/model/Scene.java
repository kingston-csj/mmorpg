package com.kingston.mmorpg.game.scene.model;

import java.util.HashMap;
import java.util.Map;

public class Scene {
	
	/** 母地图 */
	private WorldMap parent;
	
	/** 场景分屏 */
	private Map<Integer, Area> areas = new HashMap<>();

}
