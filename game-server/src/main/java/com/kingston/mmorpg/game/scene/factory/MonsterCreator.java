package com.kingston.mmorpg.game.scene.factory;

import com.kingston.mmorpg.game.database.config.domain.ConfigMonster;
import com.kingston.mmorpg.game.scene.actor.Monster;
import com.kingston.mmorpg.game.scene.actor.SceneActor;

public class MonsterCreator implements ActorCreator {

	@Override
	public SceneActor create(ActorPrototype prototype, Object... params) {
		ConfigMonster configMonster = (ConfigMonster)prototype;
		Monster monster = new Monster();
		monster.setId(configMonster.getId());
		monster.setName(configMonster.getName());
		
		return monster;
	}

}
