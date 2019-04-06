package com.kingston.mmorpg.game.scene.actor;

import com.kingston.mmorpg.game.database.user.CrudEntity;
import com.kingston.mmorpg.game.database.user.entity.PlayerEnt;

public class Player extends Creature implements CrudEntity {
	
	/**
	 * 数据载体
	 */
	private PlayerEnt playerEnt;
	
	
	public Player(long hp, int attack) {
		setHp(hp);
		setAttack(attack);
	}
	
	public Player() {
		
	}
	
	public void setPlayerEnt(PlayerEnt playerEnt) {
		this.playerEnt = playerEnt;
	}
	
	public void setLevel(int level) {
		this.playerEnt.setLevel(level);
	}
	
	public int getLevel() {
		return playerEnt.getLevel();
	}

	@Override
	public String toString() {
		return "Player [hp=" + hp + ", attack=" + attack + "]";
	}

	@Override
	public PlayerEnt getEntity() {
		return playerEnt;
	}

}
