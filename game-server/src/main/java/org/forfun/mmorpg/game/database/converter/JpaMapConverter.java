package org.forfun.mmorpg.game.database.converter;

import org.forfun.mmorpg.common.util.JsonUtil;

import javax.persistence.AttributeConverter;
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