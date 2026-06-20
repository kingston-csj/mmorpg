package org.forfun.mmorpg.game.chat.message;

import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import org.forfun.mmorpg.game.chat.ChatService;

@MessageMeta(cmd = ChatService.CMD_REQ_CHAT)
public class ReqChat implements Message {

    /**
     * 发送频道：1个人 2世界 3公会 4队伍
     */
    private byte channel;

    private String target;

    private String content;

    public byte getChannel() {
        return channel;
    }

    public void setChannel(byte channel) {
        this.channel = channel;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
