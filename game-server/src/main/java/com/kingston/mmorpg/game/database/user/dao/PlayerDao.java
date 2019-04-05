package com.kingston.mmorpg.game.database.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kingston.mmorpg.game.database.user.entity.PlayerEnt;

public interface PlayerDao extends JpaRepository<PlayerEnt, Long> {

	
	
}
