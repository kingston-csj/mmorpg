package org.forfun.mmorpg.game.chat.message

import jforgame.socket.share.annotation.MessageMeta
import jforgame.socket.share.message.Message
import org.forfun.mmorpg.game.chat.ChatService

@MessageMeta(cmd = ChatService.CMD_RES_CHAT)
class ResChat : Message
