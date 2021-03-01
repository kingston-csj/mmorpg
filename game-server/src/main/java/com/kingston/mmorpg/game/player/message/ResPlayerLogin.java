package com.kingston.mmorpg.game.player.message;

import com.kingston.mmorpg.game.player.service.PlayerService;
import com.kingston.mmorpg.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.net.socket.message.Message;
import lombok.Getter;
import lombok.Setter;

@MessageMeta(cmd = PlayerService.CMD_RES_LOGIN)
@Getter
@Setter
public class ResPlayerLogin implements Message {

	private byte status;

}
