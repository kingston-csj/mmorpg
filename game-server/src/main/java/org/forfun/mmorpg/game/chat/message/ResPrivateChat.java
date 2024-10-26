package org.forfun.mmorpg.game.chat.message;

import jforgame.socket.share.message.Message;
import lombok.Data;

@Data
public class ResPrivateChat implements Message {

    private long receiverId;

    private String content;

}
