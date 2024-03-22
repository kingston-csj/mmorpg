package org.forfun.mmorpg.game.player.message;

import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.player.service.PlayerService;

@MessageMeta(cmd = PlayerService.CMD_RES_LOGIN)
@Getter
@Setter
public class ResPlayerLogin implements Message {

	private byte status;

}
