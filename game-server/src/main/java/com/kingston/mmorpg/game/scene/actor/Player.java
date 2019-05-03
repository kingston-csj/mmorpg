package com.kingston.mmorpg.game.scene.actor;

import java.util.HashSet;
import java.util.Set;

import com.kingston.mmorpg.game.database.user.CrudEntity;
import com.kingston.mmorpg.game.database.user.entity.PlayerEnt;

public class Player extends Creature implements CrudEntity {

	/**
	 * 数据载体
	 */
	private PlayerEnt playerEnt;
	
	/**
	 * 已开启的功能列表
	 */
	private Set<Integer> functions = new HashSet<>();
	

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
	
	public void openFunction(int funcId) {
		this.functions.add(funcId);
	}
	
	public void closeFunction(int funcId) {
		this.functions.remove(funcId);
	}
	
	public boolean isOpenedFunction(int funcId) {
		return this.functions.contains(funcId);
	}
 
	@Override
	public PlayerEnt getEntity() {
		return playerEnt;
	}
	
	@Override
	public ActorType getType() {
		return ActorType.Player;
	}

}
