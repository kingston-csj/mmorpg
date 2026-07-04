package org.forfun.mmorpg.game.player.event

import jforgame.commons.eventbus.BaseEvent
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt

class PlayerLevelUpEvent(private val player: PlayerEnt) : BaseEvent {

    override fun getOwner(): PlayerEnt = player
}
