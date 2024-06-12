package org.forfun.mmorpg.game.database.config.dao;

import org.forfun.mmorpg.game.database.config.domain.ConfigFunction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigFunctionDao extends JpaRepository<ConfigFunction, Integer> {

}
