package org.forfun.mmorpg.game.database.user.dao

import org.forfun.mmorpg.game.database.user.entity.IdentityEnt
import org.springframework.data.jpa.repository.JpaRepository

interface IdentityDao : JpaRepository<IdentityEnt, Int>
