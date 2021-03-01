package com.kingston.mmorpg.game.scene.actor;

import com.kingston.mmorpg.game.ai.fsm.Scene;
import com.kingston.mmorpg.net.command.IDispatch;

/**
 * 场景里的各种演员
 * 
 * @author kingston
 */
public abstract class SceneActor extends GameObject implements IDispatch {

	protected int mapId;

	protected int lineId;

	private Scene scene;

	@Override
	public int dispatchKey() {
		return mapId;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void setId(long id) {
		this.id = id;
	}

	public abstract ActorType getType();

}
