package org.forfun.mmorpg.protocol.codec;


import org.forfun.mmorpg.protocol.Message;

/**
 * 私有协议栈消息编码器
 *
 */
public interface IMessageEncoder {

    /**
     * 把一个具体的消息序列化byte[]
     * @param message
     * @return
     */
    byte[] writeMessageBody(Message message);

}