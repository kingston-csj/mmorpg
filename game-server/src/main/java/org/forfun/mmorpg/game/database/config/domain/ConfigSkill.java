package org.forfun.mmorpg.game.database.config.domain;

import jforgame.data.annotation.DataTable;
import jforgame.data.annotation.Id;

@DataTable(name = "configskill")
public class ConfigSkill {

	@Id
	private int id;

	private String name;

	private int needLevel;

	/**
	 * 技能效果说明
	 */
	private String effect;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNeedLevel() {
		return needLevel;
	}

	public void setNeedLevel(int needLevel) {
		this.needLevel = needLevel;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

}
