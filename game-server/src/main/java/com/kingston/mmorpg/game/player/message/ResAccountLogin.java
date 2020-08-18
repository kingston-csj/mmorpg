package com.kingston.mmorpg.game.player.message;

import java.util.ArrayList;
import java.util.List;

import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.game.player.message.vo.PlayerLoginVo;
import com.kingston.mmorpg.game.player.service.PlayerService;
import lombok.Data;

@MessageMeta(cmd = PlayerService.CMD_RES_ACCOUNT_LOGIN)
@Data
public class ResAccountLogin extends Message {

	private List<PlayerLoginVo> players = new ArrayList<>();

}
