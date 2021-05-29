package org.forfun.mmorpg.game.player.message;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import lombok.Data;
import org.forfun.mmorpg.game.player.message.vo.PlayerLoginVo;
import org.forfun.mmorpg.game.player.service.PlayerService;
import org.forfun.mmorpg.protocol.annotation.MessageMeta;
import org.forfun.mmorpg.protocol.Message;

import java.util.ArrayList;
import java.util.List;

@MessageMeta(cmd = PlayerService.CMD_RES_ACCOUNT_LOGIN)
@Data
public class ResAccountLogin implements Message {

	@Protobuf
	private List<PlayerLoginVo> players = new ArrayList<>();

}
