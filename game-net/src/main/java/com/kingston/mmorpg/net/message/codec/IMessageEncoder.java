package com.kingston.mmorpg.net.message.codec;


import com.kingston.mmorpg.net.message.Message;

/**
 * 私有协议栈消息编码器
 * @author kingston
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