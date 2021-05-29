package org.forfun.mmorpg.game.player.message;

import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.player.service.PlayerService;
import org.forfun.mmorpg.protocol.annotation.MessageMeta;
import org.forfun.mmorpg.protocol.Message;

@MessageMeta(cmd = PlayerService.CMD_REQ_CREATE_NEW)
@Getter
@Setter
public class ReqCreateNewPlayer implements Message {

	private String name;

}
