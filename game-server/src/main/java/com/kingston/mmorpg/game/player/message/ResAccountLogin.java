package com.kingston.mmorpg.game.player.message;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.game.player.message.vo.PlayerLoginVo;
import com.kingston.mmorpg.game.player.service.PlayerService;
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
