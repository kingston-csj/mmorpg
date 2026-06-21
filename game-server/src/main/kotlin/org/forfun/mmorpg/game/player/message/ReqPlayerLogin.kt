package org.forfun.mmorpg.game.player.message

import jforgame.socket.share.annotation.MessageMeta
import jforgame.socket.share.message.Message
import org.forfun.mmorpg.game.player.service.PlayerService

@MessageMeta(cmd = PlayerService.CMD_REQ_PLAYER_LOGIN)
class ReqPlayerLogin : Message {

    var playerId: Long = 0
}
