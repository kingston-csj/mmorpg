package com.kingston.mmorpg.game.ai;

import com.kingston.mmorpg.game.scene.actor.Creature;
import com.kingston.mmorpg.game.scene.actor.Monster;
import com.kingston.mmorpg.game.scene.actor.Player;

public class AttackState implements State {

	@Override
	public void onEnter(Creature creature) {
		// 进入攻击状态
		
	}

	@Override
	public void onExit(Creature creature) {
		// 离开攻击状态
	}

	@Override
	public void execute(Creature creature) {
		Player player = (Player)creature;
		Scene scene = player.getScene();
		Monster monster = scene.getMonster();
		player.changeHp(-monster.getAttack());
		monster.changeHp(-player.getAttack());
		System.err.println("邂逅敌人，快使用双截棍，哼哼哈兮。"
				+ "我方血量["+ player.getHp() + "]"
				+ "敌方血量["+ monster.getHp() + "]");

	}

}
