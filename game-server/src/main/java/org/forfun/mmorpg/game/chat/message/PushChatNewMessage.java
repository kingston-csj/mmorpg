package org.forfun.mmorpg.game.chat.message;

import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import lombok.Data;
import org.forfun.mmorpg.game.chat.ChatService;

import java.util.List;

@Data
@MessageMeta(cmd = ChatService.CMD_PUSH_NEW_MESSAGES)
public class PushChatNewMessage implements Message {

    private List<ChatMessageVo> messages;
}
