package org.forfun.mmorpg.game.player.message;

import org.forfun.mmorpg.game.player.service.PlayerService;
import org.forfun.mmorpg.net.socket.annotation.MessageMeta;
import org.forfun.mmorpg.net.message.Message;
import lombok.Getter;
import lombok.Setter;

@MessageMeta(cmd = PlayerService.CMD_RES_LOGIN)
@Getter
@Setter
public class ResPlayerLogin implements Message {

	private byte status;

}