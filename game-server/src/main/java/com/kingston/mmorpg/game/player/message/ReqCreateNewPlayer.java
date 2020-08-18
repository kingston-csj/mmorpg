package com.kingston.mmorpg.game.player.message;

import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.game.player.service.PlayerService;
import lombok.Data;

@MessageMeta(cmd = PlayerService.CMD_REQ_CREATE_NEW)
@Data
public class ReqCreateNewPlayer extends Message {

	private String name;

}
