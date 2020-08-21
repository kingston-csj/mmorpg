package com.kingston.mmorpg.framework.net.socket.codec;

import com.kingston.mmorpg.game.base.SpringContext;

public class SerializerHelper {

    public static volatile SerializerHelper instance = new SerializerHelper();

    public static SerializerHelper getInstance() {
        return instance;
    }

    public IMessageDecoder getDecoder() {
        return SpringContext.getBean(SerializerFactory.class).getDecoder();
    }

    public IMessageEncoder getEncoder() {
        return SpringContext.getBean(SerializerFactory.class).getEncoder();
    }

}
