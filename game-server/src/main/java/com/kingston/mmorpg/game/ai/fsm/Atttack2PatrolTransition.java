package com.kingston.mmorpg.game.ai.fsm;

import com.kingston.mmorpg.game.database.user.entity.PlayerEnt;
import com.kingston.mmorpg.game.scene.actor.Creature;
import com.kingston.mmorpg.game.scene.actor.Monster;

public class Atttack2PatrolTransition extends Transition {

	public Atttack2PatrolTransition(State from, State to) {
		super(from, to);
	}

	@Override
	public boolean meetCondition(Creature creature) {
		PlayerEnt player = (PlayerEnt) creature;
		Scene scene = player.getScene();
		Monster monster = scene.getMonster();

		return monster.isDie();
	}

}
