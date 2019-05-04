package com.kingston.mmorpg.game.database.config.domain;

import com.kingston.mmorpg.game.scene.factory.ActorPrototype;

import lombok.Getter;

@Getter
public class ConfigMonster implements ActorPrototype {
	
	private int id;
	
	private String name;

	
}
