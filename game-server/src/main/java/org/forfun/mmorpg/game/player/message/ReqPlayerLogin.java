package org.forfun.mmorpg.game.player.message;

import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.player.service.PlayerService;

@MessageMeta(cmd = PlayerService.CMD_REQ_PLAYER_LOGIN)
@Getter
@Setter
public class ReqPlayerLogin implements Message {

	private long playerId;

}
