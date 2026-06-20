package org.forfun.mmorpg.game.chat.channel;

import org.forfun.mmorpg.game.chat.model.BaseChatMessage;

import java.util.HashMap;
import java.util.Map;

public abstract class ChatChannel {

    private static Map<Byte, ChatChannel> channels = new HashMap<>();

    public abstract ChannelType getChannelType();

    static {
        channels.put(ChannelType.PRIVATE.getType(), new PrivateChatChannel());
    }

    public static ChatChannel getChannel(byte type) {
        return channels.get(type);
    }

    public abstract boolean verifySend(BaseChatMessage message);

    public void send(BaseChatMessage message) {
        if (verifySend(message)) {
            doSend(message);
        }
    }

    public abstract void doSend(BaseChatMessage message);

}