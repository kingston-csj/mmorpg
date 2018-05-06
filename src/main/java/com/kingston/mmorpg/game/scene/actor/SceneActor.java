package com.kingston.mmorpg.game.scene.actor;

import com.kingston.mmorpg.framework.net.socket.task.IDispatch;

/**
 * 场景里的各种演员
 * @author kingston
 */
public abstract class SceneActor extends GameObject implements IDispatch {

	protected int mapId;
	
	protected int lineId;

	@Override
	public int dispatchMap() {
		return mapId;
	}

	@Override
	public int dispatchLine() {
		return lineId;
	}
	
}
