package org.forfun.mmorpg.protocol.codec.impl.protostuff;


import org.forfun.mmorpg.protocol.codec.IMessageEncoder;
import org.forfun.mmorpg.protocol.Message;

public class ProtostuffEncoder implements IMessageEncoder {

    @Override
    public byte[] writeMessageBody(Message message) {
        return ProtostuffCodecUtil.serializer(message);
    }
}
