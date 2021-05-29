package org.forfun.mmorpg.game.player.message;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.player.service.PlayerService;
import org.forfun.mmorpg.protocol.annotation.MessageMeta;
import org.forfun.mmorpg.protocol.Message;

@MessageMeta(cmd = PlayerService.CMD_REQ_SELECT_PLAYER)
@Getter
@Setter
public class ReqSelectPlayer implements Message {

	@Protobuf
	private long playerId;

}
