package org.forfun.mmorpg.game.player.message;

import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.player.service.PlayerService;
import org.forfun.mmorpg.protocol.annotation.MessageMeta;
import org.forfun.mmorpg.protocol.Message;

@MessageMeta(cmd = PlayerService.CMD_RES_LOGIN)
@Getter
@Setter
public class ResPlayerLogin implements Message {

	private byte status;

}
