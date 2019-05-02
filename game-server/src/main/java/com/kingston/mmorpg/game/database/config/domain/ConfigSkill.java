package com.kingston.mmorpg.game.database.config.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ConfigSkill {

	@Id
	@Column
	private int id;

	@Column
	private String name;

	@Column
	private int needLevel;

	/**
	 * 技能效果说明
	 */
	@Column
	private String effect;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEffect() {
		return effect;
	}

	public int getNeedLevel() {
		return needLevel;
	}

}
