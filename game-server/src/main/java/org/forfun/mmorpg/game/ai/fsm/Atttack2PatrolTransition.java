package org.forfun.mmorpg.game.ai.fsm;

import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.game.scene.actor.Creature;
import org.forfun.mmorpg.game.scene.actor.Monster;

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
