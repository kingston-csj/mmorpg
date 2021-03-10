package org.forfun.mmorpg.game.database.config.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.math.NumberUtils;

import org.forfun.mmorpg.game.function.model.FunctionOpenType;

import lombok.Getter;

@Entity
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
