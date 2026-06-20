package org.forfun.mmorpg.game.chat.message;

import jforgame.socket.share.annotation.MessageMeta;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte getChannel() {
        return channel;
    }

    public void setChannel(byte channel) {
        this.channel = channel;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public int getSenderHead() {
        return senderHead;
    }

    public void setSenderHead(int senderHead) {
        this.senderHead = senderHead;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
