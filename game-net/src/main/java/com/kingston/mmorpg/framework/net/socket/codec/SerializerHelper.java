package com.kingston.mmorpg.framework.net.socket.codec;

import com.kingston.mmorpg.framework.net.socket.codec.impl.reflect.ReflectDecoder;
import com.kingston.mmorpg.framework.net.socket.codec.impl.reflect.ReflectEncoder;

public class SerializerHelper {

    public static volatile SerializerHelper instance = new SerializerHelper();

    public static SerializerHelper getInstance() {
        return instance;
    }

    public IMessageDecoder getDecoder() {
        return new ReflectDecoder();
    }

    public IMessageEncoder getEncoder() {
        return new ReflectEncoder();
    }

}
