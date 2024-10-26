package org.forfun.mmorpg.game.database.converter;

import jakarta.persistence.AttributeConverter;
import jforgame.commons.JsonUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

public class JpaSetConverter implements AttributeConverter<Object, String> {

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        try {
            return jforgame.commons.JsonUtil.object2String(attribute);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        if (StringUtils.isEmpty(dbData)) {
            return JsonUtil.string2Object(dbData, Set.class);
        }
        return null;
    }

}