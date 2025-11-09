package org.forfun.mmorpg.game.chat.message;

import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import lombok.Data;
import org.forfun.mmorpg.game.chat.ChatService;
import org.forfun.mmorpg.game.player.service.PlayerService;

@Data
@MessageMeta(cmd = ChatService.CMD_RES_CHAT)
public class ResChat implements Message {


}
