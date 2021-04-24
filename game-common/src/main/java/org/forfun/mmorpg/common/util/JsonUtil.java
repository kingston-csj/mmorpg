package org.forfun.mmorpg.common.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.ArrayType;
import org.codehaus.jackson.map.type.MapType;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

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
            return MAPPER.readValue(json, type);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public static <T> T[] string2Array(String json, Class<T> clz) {
        ArrayType type = ArrayType.construct(typeFactory.constructType(clz));
        try {
            return MAPPER.readValue(json, type);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public static <K, V> Map<K, V> string2Map(String json, Class<K> keyClz, Class<V> valClz) {
        MapType type = typeFactory.constructMapType(HashMap.class, keyClz, valClz);
        try {
            return MAPPER.readValue(json, type);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }
}