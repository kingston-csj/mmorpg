package com.kingston.mmorpg.game.scene.actor;

public class Player extends Creature {
	
	
	public Player(long hp, int attack) {
		setHp(hp);
		setAttack(attack);
	}
	
	public Player() {
		
	}

	@Override
	public String toString() {
		return "Player [hp=" + hp + ", attack=" + attack + "]";
	}
	
}
