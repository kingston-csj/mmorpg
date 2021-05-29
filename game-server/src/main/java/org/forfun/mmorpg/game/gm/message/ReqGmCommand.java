package org.forfun.mmorpg.game.gm.message;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.protocol.annotation.MessageMeta;
import org.forfun.mmorpg.protocol.Message;

@MessageMeta(cmd = 1)
@Getter
@Setter
public class ReqGmCommand implements Message {

	@Protobuf
	private String params;

}
