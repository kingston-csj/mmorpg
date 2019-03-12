package com.kingston.mmorpg.game.ai;

import com.kingston.mmorpg.game.scene.actor.Creature;

public class PatrolState implements State {

	@Override
	public void onEnter(Creature creature) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExit(Creature creature) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute(Creature creature) {
		System.err.println("大王叫我来寻山，寻完南山寻北山");
	}

}
