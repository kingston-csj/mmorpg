package com.kingston.mmorpg.game.gm.message;

import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import lombok.Data;

@MessageMeta(cmd = 1)
@Data
public class ReqGmCommand extends Message {

	private String params;

}
