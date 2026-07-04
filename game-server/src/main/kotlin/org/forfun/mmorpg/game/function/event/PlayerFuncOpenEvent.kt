package org.forfun.mmorpg.game.function.event

import jforgame.commons.eventbus.BaseEvent
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt

class PlayerFuncOpenEvent(private val player: PlayerEnt) : BaseEvent {

    override fun getOwner(): PlayerEnt = player
}
