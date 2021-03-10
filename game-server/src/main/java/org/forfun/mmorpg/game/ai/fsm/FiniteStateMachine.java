package org.forfun.mmorpg.game.ai.fsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.forfun.mmorpg.game.scene.actor.Creature;

public class FiniteStateMachine {

	private State initState;

	private State currState;
	/** 各种状态以及对应的转换规则 */
	private Map<State, List<Transition>> state2Transtions = new HashMap<>();

	private volatile boolean running = true;

	private long freezeTimeOut;

	public void addTransition(Transition transition) {
		List<Transition> transitions = state2Transtions.get(transition.fromState());
		if (transitions == null) {
			transitions = new ArrayList<>();
			state2Transtions.put(transition.fromState(), transitions);
		}
		transitions.add(transition);
	}

	public State getInitState() {
		return initState;
	}

	public void setInitState(State initState) {
		this.initState = initState;
	}

	public void enterFrame(Creature creature) {

		if (this.currState == null) {
			this.currState = this.initState;
			this.currState.onEnter(creature);
		}

		Set<String> passed = new HashSet<>();
		String clazzName = this.currState.getClass().getName();

		for (;;) {

			if (!running) {
				if (freezeTimeOut > 0 && System.currentTimeMillis() > freezeTimeOut) {
					running = true;
				} else {
					break;
				}

			}

			this.currState.execute(creature);
			if (passed.contains(clazzName)) {
				break;
			}
			passed.add(clazzName);

			List<Transition> transitions = state2Transtions.get(this.currState);
			for (Transition transition : transitions) {
				if (transition.meetCondition(creature)) {
					this.currState.onExit(creature);
					this.currState = transition.toState();
					this.currState.onEnter(creature);
				}
			}
		}

	}

	/**
	 * 暂停ai
	 * 
	 * @param timeout
	 */
	public void freeze(long timeout) {
		this.freezeTimeOut = System.currentTimeMillis() + timeout;
	}

}
