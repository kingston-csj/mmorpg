package com.kingston.mmorpg.game.scene.actor;

import com.kingston.mmorpg.game.attribute.AttributeContaniner;
import com.kingston.mmorpg.game.attribute.AttributeType;
import com.kingston.mmorpg.game.buff.model.BuffContainer;
import com.kingston.mmorpg.game.scene.model.Life;

/**
 * 生物体，就是会动的场景演员
 * 
 * @author kingston
 */
public abstract class Creature extends SceneActor {

	private BuffContainer buffContainer;
	
	private AttributeContaniner attrContainer = new AttributeContaniner();

	private Life life;

	public long getHp() {
		return life.getCurrHp();
	}
	
	public long getAttack() {
		return attrContainer.getAttrValue(AttributeType.Attack);
	}

	public BuffContainer getBuffContainer() {
		return buffContainer;
	}

	public AttributeContaniner getAttrContainer() {
		return attrContainer;
	}

	public void setAttrContainer(AttributeContaniner attrContainer) {
		this.attrContainer = attrContainer;
	}
	
	public Life getLife() {
		return life;
	}

	public void setLife(Life life) {
		this.life = life;
	}

	public void setBuffContainer(BuffContainer buffContainer) {
		this.buffContainer = buffContainer;
	}

	public int dispatchMap() {
		return mapId;
	}

	public int dispatchLine() {
		return lineId;
	}
	
	public boolean isDie() {
		return life.getCurrHp() <= 0;
	}

}
