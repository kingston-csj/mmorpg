package org.forfun.mmorpg.game.chat.message;

import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import org.forfun.mmorpg.game.chat.ChatService;

import java.util.List;

@MessageMeta(cmd = ChatService.CMD_PUSH_NEW_MESSAGES)
public class PushChatNewMessage implements Message {

    private List<ChatMessageVo> messages;

    public List<ChatMessageVo> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessageVo> messages) {
        this.messages = messages;
    }
}
