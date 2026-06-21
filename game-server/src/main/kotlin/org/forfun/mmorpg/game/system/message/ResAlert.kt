package org.forfun.mmorpg.game.system.message

import jforgame.socket.share.annotation.MessageMeta
import jforgame.socket.share.message.Message
import org.forfun.mmorpg.game.system.PacketId

@MessageMeta(cmd = PacketId.RES_ALERT)
class ResAlert : Message {

    var i18nId: Int = 0
    var params: Array<String>? = null
}
