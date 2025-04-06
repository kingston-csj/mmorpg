package org.forfun.mmorpg.game.database.config.domain;

import jforgame.data.annotation.DataTable;
import jforgame.data.annotation.Id;
import lombok.Getter;

@DataTable(name = "configskill")
@Getter
public class ConfigSkill {

	@Id
	private int id;

	private String name;

	private int needLevel;

	/**
	 * 技能效果说明
	 */
	private String effect;

}
