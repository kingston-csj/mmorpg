package org.forfun.mmorpg.game.player.message

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf
import jforgame.socket.share.annotation.MessageMeta
import jforgame.socket.share.message.Message
import org.forfun.mmorpg.game.player.message.vo.PlayerLoginVo
import org.forfun.mmorpg.game.player.service.PlayerService

@MessageMeta(cmd = PlayerService.CMD_RES_ACCOUNT_LOGIN)
data class ResAccountLogin(
    @Protobuf var players: MutableList<PlayerLoginVo> = ArrayList()
) : Message
