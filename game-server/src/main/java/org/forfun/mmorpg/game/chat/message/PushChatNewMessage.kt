package org.forfun.mmorpg.game.chat.message

import jforgame.socket.share.annotation.MessageMeta
import jforgame.socket.share.message.Message
import org.forfun.mmorpg.game.chat.ChatService

@MessageMeta(cmd = ChatService.CMD_PUSH_NEW_MESSAGES)
class PushChatNewMessage : Message {

    var messages: MutableList<ChatMessageVo>? = null
}
