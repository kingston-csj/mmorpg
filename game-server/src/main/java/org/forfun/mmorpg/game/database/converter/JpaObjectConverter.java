package org.forfun.mmorpg.game.database.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import jakarta.persistence.AttributeConverter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class JpaObjectConverter implements AttributeConverter<Object, String> {

    protected static TypeFactory typeFactory = TypeFactory.defaultInstance();

    protected static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        // 这里要写入类和属性的完整类型信息，反序列化即使使用Object.class也能解析
        MAPPER.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        try {
            return MAPPER.writeValueAsString(attribute);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        if (StringUtils.isEmpty(dbData)) {
            return null;
        }
        try {
            JavaType type = typeFactory.constructType(Object.class);
            return MAPPER.readValue(dbData, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}