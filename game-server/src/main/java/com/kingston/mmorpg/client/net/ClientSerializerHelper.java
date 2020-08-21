package com.kingston.mmorpg.client.net;

import com.kingston.mmorpg.framework.net.socket.codec.IMessageDecoder;
import com.kingston.mmorpg.framework.net.socket.codec.IMessageEncoder;
import com.kingston.mmorpg.framework.net.socket.codec.ReflectDecoder;
import com.kingston.mmorpg.framework.net.socket.codec.ReflectEncoder;

public class ClientSerializerHelper {

    public static volatile ClientSerializerHelper instance = new ClientSerializerHelper();

    private IMessageDecoder decoder = new ReflectDecoder();

    private IMessageEncoder encoder = new ReflectEncoder();

    public static ClientSerializerHelper getInstance() {
        return instance;
    }

    public IMessageDecoder getDecoder() {
        return decoder;
    }

    public IMessageEncoder getEncoder() {
        return encoder;
    }
}
