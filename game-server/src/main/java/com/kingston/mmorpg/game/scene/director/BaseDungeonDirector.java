package com.kingston.mmorpg.game.scene.director;

import java.util.HashSet;
import java.util.Set;

/**
 * 副本默认场景管理器
 * 
 * @author kingston
 *
 */
public abstract class BaseDungeonDirector extends SceneDirector {

	protected Set<Long> histroyPlayerIds = new HashSet<>();

	protected Set<Long> alivePlayerIds = new HashSet<>();

	public abstract void init();

	public abstract void prepare();

	public abstract void start();

	public abstract void tryFinish();

	public void end() {
		settlement();
	}

	public abstract void settlement();

}
