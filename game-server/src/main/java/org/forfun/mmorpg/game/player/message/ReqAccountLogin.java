package org.forfun.mmorpg.game.player.message;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.player.service.PlayerService;

@Getter
@Setter
@MessageMeta(cmd = PlayerService.CMD_REQ_ACCOUNT_LOGIN)
public class ReqAccountLogin implements Message {

	@Protobuf
	private long accountId;

	@Protobuf
	private String password;

}
