package org.forfun.mmorpg.game.database.converter;

import com.fasterxml.jackson.databind.JavaType;
import org.apache.commons.lang3.StringUtils;
import org.forfun.mmorpg.game.util.ZipUtil;

import java.io.IOException;

public class JpaObjectZipConverter extends JpaObjectConverter {

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        try {
            return ZipUtil.compress(MAPPER.writeValueAsString(attribute));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        if (StringUtils.isEmpty(dbData)) {
            return null;
        }
        try {
            JavaType type = typeFactory.constructType(Object.class);
            return MAPPER.readValue(ZipUtil.decompress(dbData), type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}