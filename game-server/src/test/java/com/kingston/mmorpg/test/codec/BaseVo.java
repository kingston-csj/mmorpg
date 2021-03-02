package com.kingston.mmorpg.test.codec;


import com.kingston.mmorpg.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.net.message.Message;

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
