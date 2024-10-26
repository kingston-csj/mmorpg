package org.forfun.mmorpg.game.chat.channel;

import org.forfun.mmorpg.game.chat.model.BaseChatMessage;
import org.forfun.mmorpg.game.chat.model.TextChatMessage;

public class PrivateChatChannel extends ChatChannel {

    @Override
    public ChannelType getChannelType() {
        return ChannelType.PRIVATE;
    }

    @Override
    public boolean verifySend(BaseChatMessage message) {
        return true;
    }

    @Override
    public void doSend(BaseChatMessage message) {
        TextChatMessage textMessage = (TextChatMessage)message;
        long receiverId = message.getReceiverId();
    }

}