package org.forfun.mmorpg.game.player.message;

import org.forfun.mmorpg.game.player.service.PlayerService;
import org.forfun.mmorpg.net.socket.annotation.MessageMeta;
import org.forfun.mmorpg.net.message.Message;
import lombok.Getter;
import lombok.Setter;

@MessageMeta(cmd = PlayerService.CMD_REQ_CREATE_NEW)
@Getter
@Setter
public class ReqCreateNewPlayer implements Message {

	private String name;

}
