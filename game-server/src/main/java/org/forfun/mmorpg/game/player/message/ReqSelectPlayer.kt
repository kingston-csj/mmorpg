package org.forfun.mmorpg.game.player.message

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf
import jforgame.socket.share.annotation.MessageMeta
import jforgame.socket.share.message.Message
import org.forfun.mmorpg.game.player.service.PlayerService

@MessageMeta(cmd = PlayerService.CMD_REQ_SELECT_PLAYER)
data class ReqSelectPlayer(
    @Protobuf var playerId: Long = 0
) : Message
