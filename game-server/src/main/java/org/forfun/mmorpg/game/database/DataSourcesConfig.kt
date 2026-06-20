package org.forfun.mmorpg.game.database

import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
open class DataSourcesConfig {

    private val log = LoggerFactory.getLogger(DataSourcesConfig::class.java)

    @Bean(name = ["userDataSource"])
    @ConfigurationProperties(prefix = "spring.datasource.userdb")
   open fun secondaryDataSource(): DataSource {
        log.info("动态数据源建立链接")
        return DataSourceBuilder.create().build()
    }
}
