package org.forfun.mmorpg.game.cross.message

import jforgame.socket.share.annotation.MessageMeta
import jforgame.socket.share.message.Message
import org.forfun.mmorpg.game.MessageSource
import org.forfun.mmorpg.game.cross.constant.ConstantCross

@MessageMeta(cmd = ConstantCross.CMD_RESP_SERVER_LOGIN, source = MessageSource.FROM_SERVER_TO_SERVER)
class RpcRespServerLogin : Message {

    var remoteSid: Int = 0
    var serverType: Int = 0
}
