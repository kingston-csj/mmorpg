package org.forfun.mmorpg.game.chat.message

import jforgame.socket.share.annotation.MessageMeta
import jforgame.socket.share.message.Message
import org.forfun.mmorpg.game.chat.ChatService

@MessageMeta(cmd = ChatService.CMD_REQ_CHAT)
class ReqChat : Message {

    /**
     * 发送频道：1个人 2世界 3公会 4队伍
     */
    var channel: Byte = 0

    var target: String? = null

    var content: String? = null
}
