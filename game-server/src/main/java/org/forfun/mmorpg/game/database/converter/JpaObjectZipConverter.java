package org.forfun.mmorpg.game.database.converter;

import com.fasterxml.jackson.databind.JavaType;
import org.forfun.mmorpg.game.util.ZipUtil;

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