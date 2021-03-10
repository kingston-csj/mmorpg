package org.forfun.mmorpg.game.ai.fsm;

import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.game.scene.actor.Creature;

public class Attack2RunTransition extends Transition {

	public Attack2RunTransition(State from, State to) {
		super(from, to);
	}

	@Override
	public boolean meetCondition(Creature creature) {
		// 如果当前在攻击状态，且攻击力比怪物低，那就赶紧逃命吧
		PlayerEnt player = (PlayerEnt) creature;
		Scene scene = player.getScene();
		return player.getHp() < 50 // 快死啦
				|| player.getAttack() > scene.getMonster().getAttack() || Math.random() < 0.4; // 有概率逃跑，增大随机事件
	}

}
