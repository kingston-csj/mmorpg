package com.kingston.mmorpg.game.scene.actor;

import com.kingston.mmorpg.game.ai.Scene;
import com.kingston.mmorpg.game.buff.model.BuffContainer;

/**
 * 生物体，就是会动的场景演员
 * @author kingston
 */
public abstract class Creature extends SceneActor {
	
	private BuffContainer buffContainer;
	
	protected long hp;
	
	protected int attack;
	
	private Scene scene;
	
	public long getHp() {
		return hp;
	}

	public void setHp(long hp) {
		this.hp = hp;
	}
	
	public void changeHp(long changeHp) {
		this.hp += changeHp;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public boolean isDie() {
		return this.hp <= 0;
	}

	public BuffContainer getBuffContainer() {
		return buffContainer;
	}
	
	public int dispatchMap() {
		return mapId;
	}
	
	public int dispatchLine() {
		return lineId;
	}
	

}
