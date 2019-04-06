package com.kingston.mmorpg.game.database.user.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kingston.mmorpg.game.database.user.entity.PlayerEnt;
import com.kingston.mmorpg.game.player.model.PlayerProfile;

public interface PlayerDao extends JpaRepository<PlayerEnt, Long> {

	@Query("SELECT new com.kingston.mmorpg.game.player.model.PlayerProfile(playerId,accountId,name,level) FROM PlayerEnt")
    List<PlayerProfile> queryAllPlayers();
	
}
