package com.kingston.mmorpg.client.net;

import com.kingston.mmorpg.net.socket.codec.IMessageDecoder;
import com.kingston.mmorpg.net.socket.codec.IMessageEncoder;
import com.kingston.mmorpg.net.socket.codec.SerializerFactory;
import com.kingston.mmorpg.net.socket.codec.impl.json.JsonSerializerFactory;
import com.kingston.mmorpg.net.socket.codec.impl.protobuf.ProtobufSerializerFactory;
import com.kingston.mmorpg.net.socket.codec.impl.protostuff.ProtostuffSerializerFactory;
import com.kingston.mmorpg.net.socket.codec.impl.reflect.ReflectSerializerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class ClientSerializerHelper {

    public static volatile ClientSerializerHelper instance = new ClientSerializerHelper();

    private static SerializerFactory serializerFactory;

    static {
        try {
            String codecType = PropertiesLoaderUtils.loadAllProperties("application.properties").getProperty("game.socket.codec");
            if ("json".equalsIgnoreCase(codecType)) {
                serializerFactory = new JsonSerializerFactory();
            } else if ("protostuff".equalsIgnoreCase(codecType)) {
                serializerFactory = new ProtostuffSerializerFactory();
            } else if ("protobuf".equalsIgnoreCase(codecType)) {
                serializerFactory = new ProtobufSerializerFactory();
            } else {
                serializerFactory = new ReflectSerializerFactory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ClientSerializerHelper getInstance() {
        return instance;
    }

    public IMessageDecoder getDecoder() {
        return serializerFactory.getDecoder();
    }

    public IMessageEncoder getEncoder() {
        return serializerFactory.getEncoder();
    }
}
