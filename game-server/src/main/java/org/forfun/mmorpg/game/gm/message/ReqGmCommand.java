package org.forfun.mmorpg.game.gm.message;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import org.forfun.mmorpg.net.socket.annotation.MessageMeta;
import org.forfun.mmorpg.net.message.Message;
import lombok.Getter;
import lombok.Setter;

@MessageMeta(cmd = 1)
@Getter
@Setter
public class ReqGmCommand implements Message {

	@Protobuf
	private String params;

}
