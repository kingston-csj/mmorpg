package org.forfun.mmorpg.game.cross.event

import jforgame.commons.eventbus.BaseEvent

class RpcConnectedEvent : BaseEvent {

    var serverId: Int = 0
    var serverType: Int = 0

    companion object {
        fun valueOf(serverId: Int, serverType: Int): RpcConnectedEvent {
            val event = RpcConnectedEvent()
            event.serverId = serverId
            event.serverType = serverType
            return event
        }
    }
}
