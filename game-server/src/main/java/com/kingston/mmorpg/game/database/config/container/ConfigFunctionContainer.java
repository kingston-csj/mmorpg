package com.kingston.mmorpg.game.database.config.container;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.kingston.mmorpg.game.database.config.domain.ConfigFunction;

@Component
public class ConfigFunctionContainer {
	
	private Map<Integer, ConfigFunction> allFuncs = new HashMap<>();

	public Collection<ConfigFunction> queryAllFunctions() {
		return allFuncs.values();
	}
	
	public Collection<ConfigFunction> queryFunctionsBy(int type) {
		return allFuncs.values().stream().filter(func -> func.getOpenType() == type)
				.collect(Collectors.toList());
	}

}
