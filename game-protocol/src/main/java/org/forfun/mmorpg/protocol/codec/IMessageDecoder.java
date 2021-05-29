package org.forfun.mmorpg.protocol.codec;


import org.forfun.mmorpg.protocol.Message;

/**
 * 私有协议栈消息解码器
 */
public interface IMessageDecoder {

    /**
     * convert byte array to a full message
     *
     * @param msgClazz
     * @param body     message byte body
     * @return
     */
    Message readMessage(Class<?> msgClazz, byte[] body);

}