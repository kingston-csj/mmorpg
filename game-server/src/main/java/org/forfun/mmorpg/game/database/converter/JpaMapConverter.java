package org.forfun.mmorpg.game.database.converter;

import jakarta.persistence.AttributeConverter;
import org.forfun.mmorpg.game.util.JsonUtil;

import java.util.Map;

public class JpaMapConverter implements AttributeConverter<Object,String> {

    /**
     *
     * @param attribute
     * @return
     */
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
        if(dbData!=null){
            return JsonUtil.string2Object(dbData, Map.class);
        }
        return dbData;
    }

}