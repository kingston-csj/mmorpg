package org.forfun.mmorpg.game.scene.actor;

import jakarta.persistence.Transient;
import org.forfun.mmorpg.game.attribute.AttributeContainer;
import org.forfun.mmorpg.game.attribute.AttributeType;
import org.forfun.mmorpg.game.buff.model.BuffContainer;
import org.forfun.mmorpg.game.scene.model.Life;

/**
 * 生物体，就是会动的场景演员
 *
 */
public abstract class Creature extends SceneActor {

    @Transient
	private BuffContainer buffContainer;

    @Transient
	private AttributeContainer attrContainer = new AttributeContainer();

    @Transient
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

	public AttributeContainer getAttrContainer() {
		return attrContainer;
	}

	public void setAttrContainer(AttributeContainer attrContainer) {
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

	public int dispatchKey() {
		return mapId + lineId;
	}

	public int dispatchLine() {
		return lineId;
	}
	
	public boolean isDie() {
		return life.getCurrHp() <= 0;
	}

}
