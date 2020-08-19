package com.kingston.mmorpg.framework.net.socket.codec;

import com.kingston.mmorpg.framework.net.socket.codec.IMessageDecoder;
import com.kingston.mmorpg.framework.net.socket.codec.IMessageEncoder;

/**
 * 消息序列化工厂
 *
 */
public interface SerializerFactory {

    /**
     * 生成解码器
     * @return
     */
    IMessageDecoder getDecoder();

    /**
     * 生成编码器
     * @return
     */
    IMessageEncoder getEncoder();

}