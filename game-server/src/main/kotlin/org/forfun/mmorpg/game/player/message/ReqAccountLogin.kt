package org.forfun.mmorpg.game.player.message

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf
import jforgame.socket.share.annotation.MessageMeta
import jforgame.socket.share.message.Message
import org.forfun.mmorpg.game.player.service.PlayerService

@MessageMeta(cmd = PlayerService.CMD_REQ_ACCOUNT_LOGIN)
class ReqAccountLogin : Message {

    @Protobuf
    var accountId: Long = 0

    @Protobuf
    var password: String? = null
}
