package com.kingston.mmorpg.net.socket.codec.impl.protostuff;


import com.kingston.mmorpg.net.socket.codec.IMessageEncoder;
import com.kingston.mmorpg.net.socket.message.Message;

public class ProtostuffEncoder implements IMessageEncoder {

    @Override
    public byte[] writeMessageBody(Message message) {
        return ProtostuffCodecUtil.serializer(message);
    }
}
