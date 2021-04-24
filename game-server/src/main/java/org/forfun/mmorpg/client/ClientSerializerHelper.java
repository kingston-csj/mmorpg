package org.forfun.mmorpg.client;

import org.forfun.mmorpg.net.message.codec.IMessageDecoder;
import org.forfun.mmorpg.net.message.codec.IMessageEncoder;
import org.forfun.mmorpg.net.message.codec.SerializerFactory;
import org.forfun.mmorpg.net.message.codec.impl.json.JsonSerializerFactory;
import org.forfun.mmorpg.net.message.codec.impl.protobuf.ProtobufSerializerFactory;
import org.forfun.mmorpg.net.message.codec.impl.protostuff.ProtostuffSerializerFactory;
import org.forfun.mmorpg.net.message.codec.impl.reflect.ReflectSerializerFactory;
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

    public SerializerFactory getSerializerFactory() {
        return serializerFactory;
    }
}
