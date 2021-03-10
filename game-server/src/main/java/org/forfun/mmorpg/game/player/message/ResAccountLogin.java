package org.forfun.mmorpg.game.player.message;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import org.forfun.mmorpg.game.player.service.PlayerService;
import org.forfun.mmorpg.net.socket.annotation.MessageMeta;
import org.forfun.mmorpg.net.message.Message;
import org.forfun.mmorpg.game.player.message.vo.PlayerLoginVo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@MessageMeta(cmd = PlayerService.CMD_RES_ACCOUNT_LOGIN)
@Data
//@EqualsAndHashCode(callSuper=false)
public class ResAccountLogin implements Message {

	@Protobuf
	private List<PlayerLoginVo> players = new ArrayList<>();

}
