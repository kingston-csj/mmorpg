package org.forfun.mmorpg.test.codec;


import org.forfun.mmorpg.net.socket.annotation.MessageMeta;
import org.forfun.mmorpg.net.message.Message;

@MessageMeta(cmd = 1)
public class BaseVo  implements Message {
	
	protected long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	

}
