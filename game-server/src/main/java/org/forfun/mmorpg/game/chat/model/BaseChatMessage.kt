package org.forfun.mmorpg.game.chat.model

abstract class BaseChatMessage {

    var channelType: Byte = 0
    var senderId: Long = 0
    var receiverId: Long = 0
}
