package org.forfun.mmorpg.game.scene.actor;

public class Monster extends Creature {
	
	private int modelId;
	
	private String name;
	

	public Monster() {
	}


	public int getModelId() {
		return modelId;
	}


	public void setModelId(int modelId) {
		this.modelId = modelId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public ActorType getType() {
		return ActorType.Monster;
	}
	
}
