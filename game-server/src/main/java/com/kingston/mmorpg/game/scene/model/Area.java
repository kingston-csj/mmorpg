package com.kingston.mmorpg.game.scene.model;

import com.kingston.mmorpg.game.database.user.entity.PlayerEnt;
import com.kingston.mmorpg.game.scene.actor.SceneActor;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基于网格的AOI算法 将场景划分为等大的小区域
 * 
 * @author kingston
 *
 */
public class Area {

	private int id;

	/** 区域宽度（单位为像素） */
	public static final int WIDTH = 300;
	/** 区域高度（单位为像素） */
	public static final int HEIGHT = 300;

	private ConcurrentMap<Long, SceneActor> actors = new ConcurrentHashMap<>();

	private AtomicInteger playerSum = new AtomicInteger(0);

	public Area(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void add(SceneActor actor) {
		if (this.actors.put(actor.getId(), actor) == null) {
			if (actor instanceof PlayerEnt) {
				playerSum.incrementAndGet();
			}
		}
	}

	public void remove(SceneActor actor) {
		if (this.actors.remove(actor.getId()) != null) {
			if (actor instanceof PlayerEnt) {
				playerSum.decrementAndGet();
			}
		}
	}

	public Collection<SceneActor> getActors() {
		return this.actors.values();
	}

}
