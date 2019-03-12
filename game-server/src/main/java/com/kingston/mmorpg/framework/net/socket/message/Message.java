package com.kingston.mmorpg.framework.net.socket.message;

import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;

/**
 * 抽象消息定义
 */
public abstract class Message {
	

	/**
	 * messageMeta, module of message
	 * @return
	 */
	public short getModule() {
		MessageMeta annotation = getClass().getAnnotation(MessageMeta.class);
		if (annotation != null) {
			return annotation.module();
		}
		return 0;
	}

	/**
	 * messageMeta, subType of message
	 * @return
	 */
	public short getCmd() {
		MessageMeta annotation = getClass().getAnnotation(MessageMeta.class);
		if (annotation != null) {
			return annotation.cmd();
		}
		return 0;
	}

	public String key() {
		return this.getModule() + "_" + this.getCmd();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getCmd();
		result = prime * result + getModule();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (getCmd() != other.getCmd())
			return false;
		if (getModule() != other.getModule())
			return false;
		return true;
	}
	
	

}
