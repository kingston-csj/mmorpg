package com.kingston.mmorpg.game.database.config.domain;

import org.apache.commons.lang3.math.NumberUtils;

import com.kingston.mmorpg.game.function.model.FunctionOpenType;

public class ConfigFunction {
	
	private int id;
	
	/**
	 * 开启类型 {@link FunctionOpenType#getType()}
	 */
	private int openType;
	
	/**
	 * 开启类型对应的参数
	 */
	private String openParams;

	public int getId() {
		return id;
	}

	public int getOpenType() {
		return openType;
	}

	public String getOpenParams() {
		return openParams;
	}
	
	public int getOpenMainParam() {
		return NumberUtils.toInt(openParams);
	}

}
