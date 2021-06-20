package org.forfun.mmorpg.mq;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.forfun.mmorpg.protocol.Message;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public interface MqMessage extends Message {

    /**
     *  server id whe receive this message
     * @return
     */
    int receiver();

}
