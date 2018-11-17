package com.kingston.mmorpg.game.database.config.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kingston.mmorpg.game.database.config.domain.ConfigSkill;

public interface ConfigSkillDao extends JpaRepository<ConfigSkill, Integer> {

}
