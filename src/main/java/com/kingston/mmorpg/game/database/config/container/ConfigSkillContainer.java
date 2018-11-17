package com.kingston.mmorpg.game.database.config.container;

import java.util.HashMap;
import java.util.Map;

import com.kingston.mmorpg.game.database.config.Reloadable;
import com.kingston.mmorpg.game.database.config.domain.ConfigSkill;

public class ConfigSkillContainer implements Reloadable {
	
	private Map<Integer, ConfigSkill> skills = new HashMap<>();

	@Override
	public void reload() {
		// TODO Auto-generated method stub
		
	}

}
