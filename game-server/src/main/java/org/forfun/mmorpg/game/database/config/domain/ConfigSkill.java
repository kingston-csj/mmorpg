package org.forfun.mmorpg.game.database.config.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

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
