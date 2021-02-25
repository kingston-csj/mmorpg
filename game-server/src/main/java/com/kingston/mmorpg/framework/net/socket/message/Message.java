package com.kingston.mmorpg.framework.net.socket.message;

import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
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
		MessageMeta annotation = getClass().getAnnotation(MessageMeta.class);
		if (annotation != null) {
			return annotation.cmd();
		}
		return 0;
	}

}
