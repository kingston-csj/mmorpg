package org.forfun.mmorpg.mq;


import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public interface MqMessage {

    /**
     *  server id whe receive this message
     * @return
     */
    int receiver();

}
