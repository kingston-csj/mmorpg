package com.kingston.mmorpg.framework.net.socket.codec.impl.json;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;

public class JsonUtil {

    private static TypeFactory typeFactory = TypeFactory.defaultInstance();

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public static String object2String(Object object) {
        StringWriter writer = new StringWriter();
        try {
            MAPPER.writeValue(writer, object);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
        return writer.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> T string2Object(String json, Class<T> clazz) {
        JavaType type = typeFactory.constructType(clazz);
        try {
            return (T) MAPPER.readValue(json, type);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }
}