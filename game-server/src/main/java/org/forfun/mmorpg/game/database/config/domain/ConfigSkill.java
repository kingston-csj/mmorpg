package org.forfun.mmorpg.game.database.config.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;

@Entity(name = "configskill")
@Getter
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

}
