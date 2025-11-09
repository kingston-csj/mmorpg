package org.forfun.mmorpg.game.chat.message;

import jforgame.socket.share.annotation.MessageMeta;
import lombok.Data;

@Data
@MessageMeta()
public class ChatMessageVo {

    private String id;

    /**
     * 聊天频道 1私聊 2公会 3世界 4队伍
     */
    private byte channel;

    /**
     * 发言人id
     */
    private String senderId;
    /**
     * 发言人昵称
     */
    private String senderName;

    /**
     * 发言人头像
     */
    private int senderHead;
    /**
     * 接收者id, 如果是公会, 则为公会id
     * 如果是私聊, 则为玩家id
     */
    private String receiverId;

    /**
     * 发送时间戳
     */
    private long timestamp;

    /**
     * 聊天内容
     */
    private String content;
}
