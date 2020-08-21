package com.kingston.mmorpg.game.player.message;

//import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.game.player.service.PlayerService;
import lombok.Getter;
import lombok.Setter;

@MessageMeta(cmd = PlayerService.CMD_REQ_SELECT_PLAYER)
@Getter
@Setter
public class ReqSelectPlayer extends Message {

//	@Protobuf
	private long playerId;

}
