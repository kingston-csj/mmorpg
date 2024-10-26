package org.forfun.mmorpg.game.chat.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseChatMessage {

    protected byte channelType;

    protected long senderId;

    protected long receiverId;

}