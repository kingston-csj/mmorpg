package com.kingston.mmorpg.game.database.config.container;

import com.kingston.mmorpg.game.database.config.dao.${config}Dao;
import com.kingston.mmorpg.game.database.config.domain.${config};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ConfigSkillContainer implements ReloadableContainer<Integer, ${config}> {

	@Autowired
	private ${config}Dao ${daoName}Dao;

	private Map<${idType}, ${config}> ${dataName} = new HashMap<>();

	@Override
	public void reload() {
		${dataName} = ${daoName}Dao.findAll().stream().collect(Collectors.toMap(${config}::getId, Function.identity()));
	}

	@Override
	public void selfChecking() {
	}

	public ${config} queryOne(Integer id) {
		return ${dataName}.get(id);
	}

	public Collection<${config}> queryAll() {
		return ${dataName}.values();
	}

}
