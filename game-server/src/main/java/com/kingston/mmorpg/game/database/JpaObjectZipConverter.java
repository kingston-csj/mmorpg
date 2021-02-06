package com.kingston.mmorpg.game.database;

import com.kingston.mmorpg.game.util.ZipUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;

import javax.persistence.AttributeConverter;
import java.io.IOException;

public class JpaObjectZipConverter extends JpaObjectConverter {

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        try {
            return ZipUtil.compress(MAPPER.writeValueAsString(attribute));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        if (dbData.isEmpty()) {
            return null;
        }
        try {
            JavaType type = typeFactory.constructType(Object.class);
            return MAPPER.readValue(ZipUtil.decompress(dbData), type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}