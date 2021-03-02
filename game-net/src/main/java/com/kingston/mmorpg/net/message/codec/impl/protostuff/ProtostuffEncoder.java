package com.kingston.mmorpg.net.message.codec.impl.protostuff;


import com.kingston.mmorpg.net.message.codec.IMessageEncoder;
import com.kingston.mmorpg.net.message.Message;

public class ProtostuffEncoder implements IMessageEncoder {

    @Override
    public byte[] writeMessageBody(Message message) {
        return ProtostuffCodecUtil.serializer(message);
    }
}
