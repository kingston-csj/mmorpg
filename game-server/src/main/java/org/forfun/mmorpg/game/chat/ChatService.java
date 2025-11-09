package org.forfun.mmorpg.game.chat;

import org.forfun.mmorpg.game.chat.channel.ChannelType;
import org.forfun.mmorpg.game.chat.channel.ChatChannel;
import org.forfun.mmorpg.game.chat.model.TextChatMessage;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    public static final byte CMD_REQ_CHAT = 1;

    public static final byte CMD_RES_CHAT = 51;

    public static final byte CMD_PUSH_NEW_MESSAGES = 99;

    public void sendMessage(PlayerEnt player, byte channel, long receiverId, String content) {
        TextChatMessage textMsg = new TextChatMessage();
        textMsg.setChannelType(channel);
        textMsg.setReceiverId(receiverId);
        textMsg.setText(content);
        ChatChannel.getChannel(channel).send(textMsg);
    }

}
