package org.forfun.mmorpg.game.database.config.dao;

import org.forfun.mmorpg.game.database.config.domain.ConfigCommonValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigCommonValueDao extends JpaRepository<ConfigCommonValue, Integer> {
}
