package com.kingston.mmorpg.game.database.config.container;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingston.mmorpg.game.database.config.Reloadable;
import com.kingston.mmorpg.game.database.config.dao.ConfigSkillDao;
import com.kingston.mmorpg.game.database.config.domain.ConfigSkill;

@Component
public class ConfigSkillContainer implements ReloadableContainer<Integer, ConfigSkill> {

	@Autowired
	private ConfigSkillDao skillDao;

	private Map<Integer, ConfigSkill> skills = new HashMap<>();

	@Override
	public void reload() {
		skills = skillDao.findAll().stream().collect(Collectors.toMap(ConfigSkill::getId, Function.identity()));
	}

	public ConfigSkill queryOne(Integer id) {
		return skills.get(id);
	}

	public Collection<ConfigSkill> queryAll() {
		return skills.values();
	}

}
