package org.forfun.mmorpg.game.scene.actor;

import org.forfun.mmorpg.game.ai.fsm.Scene;

/**
 * 场景里的各种演员
 *
 */
public abstract class SceneActor extends GameObject {

	protected int mapId;

	protected int lineId;

	private Scene scene;

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
