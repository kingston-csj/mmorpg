package com.kingston.mmorpg.framework.net.socket.protostuff;

import com.kingston.mmorpg.framework.net.socket.codec.IMessageEncoder;
import com.kingston.mmorpg.framework.net.socket.message.Message;

public class ProtostuffEncoder implements IMessageEncoder {

    @Override
    public byte[] writeMessageBody(Message message) {
        return ProtostuffCodecUtil.serializer(message);
    }
}
