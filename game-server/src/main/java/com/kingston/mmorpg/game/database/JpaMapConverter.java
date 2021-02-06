package com.kingston.mmorpg.game.database;

import com.kingston.mmorpg.game.util.JsonUtil;

import javax.persistence.AttributeConverter;
import java.util.Map;
import java.util.Set;

public class JpaMapConverter implements AttributeConverter<Object,String> {

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