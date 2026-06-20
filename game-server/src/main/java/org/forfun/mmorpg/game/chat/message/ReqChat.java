package org.forfun.mmorpg.game.chat.message;

import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import lombok.Data;
import org.forfun.mmorpg.game.chat.ChatService;

@Data
@MessageMeta(cmd = ChatService.CMD_REQ_CHAT)
public class ReqChat implements Message {

    /**
     * 发送频道：1个人 2世界 3公会 4队伍
     */
    private byte channel;

    private String target;

    private String content;

}
