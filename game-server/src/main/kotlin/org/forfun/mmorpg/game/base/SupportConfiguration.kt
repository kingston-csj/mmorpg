package org.forfun.mmorpg.game.base

import jforgame.commons.eventbus.EventBus
import jforgame.data.reader.DataReader
import jforgame.data.reader.ExcelDataReader
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.ConversionService

@Configuration
open class SupportConfiguration {

    // 明确指定要注入的ConversionService Bean名称
    @Bean
    @ConditionalOnMissingBean
    open fun createDataReader(
        @Qualifier("dataConversionService") dataConversionService: ConversionService
    ): DataReader {
        return ExcelDataReader(dataConversionService)
    }

    @Bean
    open fun createEventBus(): EventBus {
        return EventBus()
    }
}
