package org.forfun.mmorpg.game.database.user.dao

import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.forfun.mmorpg.game.player.model.PlayerProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlayerDao : JpaRepository<PlayerEnt, Long> {

    @Query("SELECT new org.forfun.mmorpg.game.player.model.PlayerProfile(id,accountId,name,level) FROM PlayerEnt")
    fun queryAllPlayers(): List<PlayerProfile>
}
