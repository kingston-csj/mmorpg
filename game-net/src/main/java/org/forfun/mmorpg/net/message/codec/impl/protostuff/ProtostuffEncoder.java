package org.forfun.mmorpg.net.message.codec.impl.protostuff;


import org.forfun.mmorpg.net.message.codec.IMessageEncoder;
import org.forfun.mmorpg.net.message.Message;

public class ProtostuffEncoder implements IMessageEncoder {

    @Override
    public byte[] writeMessageBody(Message message) {
        return ProtostuffCodecUtil.serializer(message);
    }
}
