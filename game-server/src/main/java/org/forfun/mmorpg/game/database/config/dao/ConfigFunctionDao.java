package org.forfun.mmorpg.game.database.config.dao;

import org.forfun.mmorpg.game.database.config.domain.ConfigFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ConfigFunctionDao extends JpaRepository<ConfigFunction, Integer> {

}
