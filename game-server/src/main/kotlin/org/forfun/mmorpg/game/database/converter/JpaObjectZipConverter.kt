package org.forfun.mmorpg.game.database.converter

import com.fasterxml.jackson.databind.JavaType
import org.apache.commons.lang3.StringUtils
import org.forfun.mmorpg.game.util.ZipUtil
import java.io.IOException

class JpaObjectZipConverter : JpaObjectConverter() {

    override fun convertToDatabaseColumn(attribute: Any?): String? {
        return try {
            ZipUtil.compress(MAPPER.writeValueAsString(attribute))
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    override fun convertToEntityAttribute(dbData: String?): Any? {
        if (StringUtils.isEmpty(dbData)) {
            return null
        }
        return try {
            val type: JavaType = typeFactory.constructType(Any::class.java)
            MAPPER.readValue(ZipUtil.decompress(dbData!!), type)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}
