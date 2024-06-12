package org.forfun.mmorpg.game.database.config.dao;

import org.forfun.mmorpg.game.database.config.domain.ConfigSkill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigSkillDao extends JpaRepository<ConfigSkill, Integer> {

}
