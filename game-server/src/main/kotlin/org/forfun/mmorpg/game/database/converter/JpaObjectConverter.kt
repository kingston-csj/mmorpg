package org.forfun.mmorpg.game.database.converter

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.TypeFactory
import jakarta.persistence.AttributeConverter
import org.springframework.util.StringUtils
import java.io.IOException

open class JpaObjectConverter : AttributeConverter<Any?, String?> {

    companion object {
        @JvmStatic
        protected val typeFactory: TypeFactory = TypeFactory.defaultInstance()

        @JvmStatic
        protected val MAPPER: ObjectMapper

        init {
            MAPPER = ObjectMapper()
            // 这里要写入类和属性的完整类型信息，反序列化即使使用Object.class也能解析
            MAPPER.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
            MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL)
            MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            MAPPER.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
            MAPPER.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }
    }

    override fun convertToDatabaseColumn(attribute: Any?): String? {
        return try {
            MAPPER.writeValueAsString(attribute)
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
            MAPPER.readValue(dbData, type)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}
