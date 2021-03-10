package org.forfun.mmorpg.game.ai.fsm;

import org.forfun.mmorpg.game.scene.actor.Creature;

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
