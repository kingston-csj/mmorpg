package org.forfun.mmorpg.game.database.converter;

import org.forfun.mmorpg.game.util.JsonUtil;

import javax.persistence.AttributeConverter;
import java.util.Set;

public class JpaSetConverter implements AttributeConverter<Object, String> {

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        try {
            return JsonUtil.object2String(attribute);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        if (dbData != null) {
            return JsonUtil.string2Object(dbData, Set.class);
        }
        return null;
    }

}