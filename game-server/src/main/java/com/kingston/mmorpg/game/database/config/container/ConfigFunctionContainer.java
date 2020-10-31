package com.kingston.mmorpg.game.database.config.container;

import com.kingston.mmorpg.game.database.config.dao.ConfigFunctionDao;
import com.kingston.mmorpg.game.database.config.domain.ConfigFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ConfigFunctionContainer implements ReloadableContainer<Integer, ConfigFunction> {

	@Autowired
	private ConfigFunctionDao dao;

	private Map<Integer, ConfigFunction> allFuncs = new HashMap<>();

	public Collection<ConfigFunction> queryFunctionsBy(int type) {
		return allFuncs.values().stream().filter(func -> func.getOpenType() == type)
				.collect(Collectors.toList());
	}

	@Override
	public void reload() {
		allFuncs = dao.findAll().stream().collect(Collectors.toMap(ConfigFunction::getId, Function.identity()));
	}

	@Override
	public ConfigFunction queryOne(Integer id) {
		return allFuncs.get(id);
	}

	@Override
	public Collection<ConfigFunction> queryAll() {
		return allFuncs.values();
	}
}
