package org.forfun.mmorpg.game.gm.message;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import lombok.Getter;
import lombok.Setter;

@MessageMeta(cmd = 1)
@Getter
@Setter
public class ReqGmCommand implements Message {

	@Protobuf
	private String params;

}
