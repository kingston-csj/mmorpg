package org.forfun.mmorpg.game.scene.model;

import java.util.HashMap;
import java.util.Map;

import org.forfun.mmorpg.game.scene.actor.SceneActor;

/**
 * aoi分屏算法视野观察者
 * 
 *
 */
public class AoiObserver {

	/** 当前可见视野元素 */
	private Map<Long, SceneActor> actors = new HashMap<>();

}
