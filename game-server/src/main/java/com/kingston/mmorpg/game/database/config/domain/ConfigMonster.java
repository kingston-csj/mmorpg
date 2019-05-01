package com.kingston.mmorpg.game.database.config.domain;

import com.kingston.mmorpg.game.scene.factory.ActorPrototype;

public class ConfigMonster implements ActorPrototype {
	
	private int id;
	
	private String name;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
}
