package com.kingston.mmorpg.game.ai;

import com.kingston.mmorpg.game.scene.actor.Monster;
import com.kingston.mmorpg.game.scene.actor.Player;

public class AiTest {

	public static void main(String[] args) throws Exception {
		Player player = new Player(100, 15);
		Monster monster = new Monster(120, 10);

		Scene scene = new Scene();
		scene.setPlayer(player);
		scene.setMonster(monster);

		player.setScene(scene);
		monster.setScene(scene);

		State patrolState = new PatrolState();
		State attackState = new AttackState();
		State runState = new RunAwayState();

		Transition transition1 = new Patrol2AttackTransition(patrolState, attackState);
		Transition transition2 = new Attack2RunTransition(attackState, runState);
		Transition transition3 = new Atttack2PatrolTransition(attackState, patrolState);
		Transition transition4 = new Run2PatrolTransition(runState, patrolState);

		FiniteStateMachine fsm = new FiniteStateMachine();
		fsm.setInitState(patrolState);

		fsm.addTransition(transition1);
		fsm.addTransition(transition2);
		fsm.addTransition(transition3);
		fsm.addTransition(transition4);

		while (true) {
			fsm.enterFrame(player);
			Thread.sleep(500);
		}

	}

}
