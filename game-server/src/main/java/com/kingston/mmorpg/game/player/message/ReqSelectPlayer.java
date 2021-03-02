package com.kingston.mmorpg.game.player.message;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.kingston.mmorpg.game.player.service.PlayerService;
import com.kingston.mmorpg.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.net.message.Message;
import lombok.Getter;
import lombok.Setter;

@MessageMeta(cmd = PlayerService.CMD_REQ_SELECT_PLAYER)
@Getter
@Setter
public class ReqSelectPlayer implements Message {

	@Protobuf
	private long playerId;

}
