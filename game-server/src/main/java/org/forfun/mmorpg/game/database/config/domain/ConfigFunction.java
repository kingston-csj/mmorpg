package org.forfun.mmorpg.game.database.config.domain;

import jforgame.data.annotation.DataTable;
import jforgame.data.annotation.Id;
import jforgame.data.annotation.Index;
import org.apache.commons.lang3.math.NumberUtils;
import org.forfun.mmorpg.game.function.model.FunctionOpenType;

@DataTable(name = "configfunction")
public class ConfigFunction {
	
	@Id
	private int id;
	
	/**
	 * 开启类型 {@link FunctionOpenType#getType()}
	 */
	private FunctionOpenType openType;
	
	/**
	 * 开启类型对应的参数
	 */
	private String openParams;

	public int getOpenMainParam() {
		return NumberUtils.toInt(openParams);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public FunctionOpenType getOpenType() {
		return openType;
	}

	public void setOpenType(FunctionOpenType openType) {
		this.openType = openType;
	}

	public String getOpenParams() {
		return openParams;
	}

	public void setOpenParams(String openParams) {
		this.openParams = openParams;
	}

}
