package com.kingston.mmorpg.game.scene.model;

import java.util.HashMap;
import java.util.Map;

import com.kingston.mmorpg.game.scene.actor.SceneActor;

/**
 * aoi分屏算法视野观察者
 * @author kingston
 */
public class AoiObserver {
	
	/** 当前可见视野元素 */
	private Map<Long, SceneActor> actors = new HashMap<>();

}
