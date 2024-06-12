package org.forfun.mmorpg.game.database.config.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import org.apache.commons.lang3.math.NumberUtils;

import org.forfun.mmorpg.game.function.model.FunctionOpenType;

import lombok.Getter;

@Entity(name = "configfunction")
@Getter
public class ConfigFunction {
	
	@Id
	@Column
	private int id;
	
	/**
	 * 开启类型 {@link FunctionOpenType#getType()}
	 */
	@Column
	private int openType;
	
	/**
	 * 开启类型对应的参数
	 */
	@Column
	private String openParams;

	public int getOpenMainParam() {
		return NumberUtils.toInt(openParams);
	}

}
