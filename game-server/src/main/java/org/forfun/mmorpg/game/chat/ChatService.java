package org.forfun.mmorpg.game.chat;

import org.forfun.mmorpg.game.chat.channel.ChannelType;
import org.forfun.mmorpg.game.chat.channel.ChatChannel;
import org.forfun.mmorpg.game.chat.model.TextChatMessage;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    public void privateChat(long receiverId, String content) {
        TextChatMessage textMsg = new TextChatMessage();
        textMsg.setChannelType(ChannelType.PRIVATE.getType());
        textMsg.setReceiverId(receiverId);
        textMsg.setText(content);
        ChatChannel.getChannel(ChannelType.PRIVATE).send(textMsg);
    }

}
