package org.forfun.mmorpg.game.database.config.domain;

import org.forfun.mmorpg.game.scene.factory.ActorPrototype;

public class ConfigMonster implements ActorPrototype {
	
	private int id;
	
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
