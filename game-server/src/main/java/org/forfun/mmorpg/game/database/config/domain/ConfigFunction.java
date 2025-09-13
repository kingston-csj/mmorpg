package org.forfun.mmorpg.game.database.config.domain;

import jforgame.data.annotation.DataTable;
import jforgame.data.annotation.Id;
import jforgame.data.annotation.Index;
import lombok.Getter;
import org.apache.commons.lang3.math.NumberUtils;
import org.forfun.mmorpg.game.function.model.FunctionOpenType;

@DataTable(name = "configfunction")
@Getter
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

}
