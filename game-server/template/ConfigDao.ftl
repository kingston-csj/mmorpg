package com.kingston.mmorpg.game.database.config.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.kingston.mmorpg.game.database.config.domain.${config};

@Component
public interface ${config}Dao extends JpaRepository<${config}, ${idType}> {

}
