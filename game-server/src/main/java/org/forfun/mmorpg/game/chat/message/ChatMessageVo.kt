package org.forfun.mmorpg.game.chat.message

import jforgame.socket.share.annotation.MessageMeta

@MessageMeta
class ChatMessageVo {

    var id: String? = null

    /**
     * 聊天频道 1私聊 2公会 3世界 4队伍
     */
    var channel: Byte = 0

    /**
     * 发言人id
     */
    var senderId: String? = null

    /**
     * 发言人昵称
     */
    var senderName: String? = null

    /**
     * 发言人头像
     */
    var senderHead: Int = 0

    /**
     * 接收者id, 如果是公会, 则为公会id
     * 如果是私聊, 则为玩家id
     */
    var receiverId: String? = null

    /**
     * 发送时间戳
     */
    var timestamp: Long = 0

    /**
     * 聊天内容
     */
    var content: String? = null
}
