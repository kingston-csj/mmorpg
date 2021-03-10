package org.forfun.mmorpg.game.ai.fsm;

import org.forfun.mmorpg.game.scene.actor.Creature;

public abstract class Transition {

	/** 开始状态 */
	private State from;
	/** 结束状态 */
	private State to;

	public Transition(State from, State to) {
		this.from = from;
		this.to = to;
	}

	/**
	 * 条件判定
	 * 
	 * @param creature
	 * @return
	 */
	public abstract boolean meetCondition(Creature creature);

	public State fromState() {
		return this.from;
	}

	public State toState() {
		return this.to;
	}

}
