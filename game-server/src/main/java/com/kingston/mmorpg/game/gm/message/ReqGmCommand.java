package com.kingston.mmorpg.game.gm.message;

//import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import lombok.Getter;
import lombok.Setter;

@MessageMeta(cmd = 1)
@Getter
@Setter
public class ReqGmCommand extends Message {

//	@Protobuf
	private String params;

}
