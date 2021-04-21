package org.forfun.mmorpg.net.message;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 抽象消息定义
 */
public interface Message {

	/**
	 * messageMeta, subType of message
	 * @return
	 */
	@JsonIgnore
	default short getCmd() {
		return MessageFactory.getInstance().getMessageId(getClass());
	}

}
