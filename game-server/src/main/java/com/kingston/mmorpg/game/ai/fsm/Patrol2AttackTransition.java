package com.kingston.mmorpg.game.ai.fsm;

import com.kingston.mmorpg.game.scene.actor.Creature;
import com.kingston.mmorpg.game.scene.actor.Player;

public class Patrol2AttackTransition extends Transition {

	public Patrol2AttackTransition(State from, State to) {
		super(from, to);
	}

	@Override
	public boolean meetCondition(Creature creature) {
		// 如果当前在巡逻状态，且怪物没死，就揍它
		Player player = (Player) creature;
		Scene scene = player.getScene();

		return !scene.getMonster().isDie();
	}
	
}
