package org.forfun.mmorpg.game.scene.factory;

import org.forfun.mmorpg.game.database.config.domain.ConfigMonster;
import org.forfun.mmorpg.game.scene.actor.Monster;
import org.forfun.mmorpg.game.scene.actor.SceneActor;

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
