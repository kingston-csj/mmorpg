package org.forfun.mmorpg.client;

import org.forfun.mmorpg.protocol.codec.IMessageDecoder;
import org.forfun.mmorpg.protocol.codec.IMessageEncoder;
import org.forfun.mmorpg.protocol.codec.SerializerFactory;
import org.forfun.mmorpg.protocol.codec.impl.json.JsonSerializerFactory;
import org.forfun.mmorpg.protocol.codec.impl.protobuf.ProtobufSerializerFactory;
import org.forfun.mmorpg.protocol.codec.impl.protostuff.ProtostuffSerializerFactory;
import org.forfun.mmorpg.protocol.codec.impl.reflect.ReflectSerializerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class ClientSerializerHelper {

    public static volatile ClientSerializerHelper instance = new ClientSerializerHelper();

    private static SerializerFactory serializerFactory;

    static {
        try {
            String codecType = PropertiesLoaderUtils.loadAllProperties("application.properties").getProperty("game.socket.codec");

            serializerFactory = switch (codecType) {
                case "json" -> new JsonSerializerFactory();
                case "protostuff" -> new ProtostuffSerializerFactory();
                case "protobuf" -> new ProtobufSerializerFactory();
                default -> new ReflectSerializerFactory();
            };
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
