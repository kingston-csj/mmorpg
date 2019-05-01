package com.kingston.mmorpg.game.ai.fsm;

import com.kingston.mmorpg.game.ai.fsm.State;
import com.kingston.mmorpg.game.ai.fsm.Transition;
import com.kingston.mmorpg.game.scene.actor.Creature;
import com.kingston.mmorpg.game.scene.actor.Monster;
import com.kingston.mmorpg.game.scene.actor.Player;

public class Atttack2PatrolTransition extends Transition {

	public Atttack2PatrolTransition(State from, State to) {
		super(from, to);
	}

	@Override
	public boolean meetCondition(Creature creature) {
		Player player = (Player) creature;
		Scene scene = player.getScene();
		Monster monster = scene.getMonster();

		return monster.isDie();
	}

}
