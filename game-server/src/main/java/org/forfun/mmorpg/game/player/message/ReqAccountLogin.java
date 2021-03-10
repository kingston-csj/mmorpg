package org.forfun.mmorpg.game.player.message;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import org.forfun.mmorpg.game.player.service.PlayerService;
import org.forfun.mmorpg.net.socket.annotation.MessageMeta;
import org.forfun.mmorpg.net.message.Message;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MessageMeta(cmd = PlayerService.CMD_REQ_ACCOUNT_LOGIN)
public class ReqAccountLogin implements Message {

	@Protobuf
	private long accountId;

	@Protobuf
	private String password;

}
