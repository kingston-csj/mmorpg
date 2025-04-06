package org.forfun.mmorpg.game.database;

import lombok.extern.slf4j.Slf4j;
import org.forfun.mmorpg.game.logger.LoggerUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DataSourcesConfig {


	@Bean(name = "userDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.userdb")
	public DataSource secondaryDataSource() {
		log.info("动态数据源建立链接");
		return DataSourceBuilder.create().build();
	}
}
