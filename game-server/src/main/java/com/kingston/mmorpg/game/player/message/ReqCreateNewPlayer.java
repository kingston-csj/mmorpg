package com.kingston.mmorpg.game.player.message;

import com.kingston.mmorpg.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.net.message.Message;
import com.kingston.mmorpg.game.player.service.PlayerService;
import lombok.Getter;
import lombok.Setter;

@MessageMeta(cmd = PlayerService.CMD_REQ_CREATE_NEW)
@Getter
@Setter
public class ReqCreateNewPlayer implements Message {

	private String name;

}
