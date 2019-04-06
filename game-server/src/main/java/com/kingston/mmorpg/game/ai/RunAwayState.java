package com.kingston.mmorpg.game.ai;

import com.kingston.mmorpg.game.scene.actor.Creature;

public class RunAwayState implements State {

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
		System.err.println("三十六计，走为上计");
	}

}
