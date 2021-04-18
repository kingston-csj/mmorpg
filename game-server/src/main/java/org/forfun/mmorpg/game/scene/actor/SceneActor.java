package org.forfun.mmorpg.game.scene.actor;

import org.forfun.mmorpg.game.ai.fsm.Scene;
import org.forfun.mmorpg.net.dispatcher.IDispatch;

/**
 * 场景里的各种演员
 *
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
