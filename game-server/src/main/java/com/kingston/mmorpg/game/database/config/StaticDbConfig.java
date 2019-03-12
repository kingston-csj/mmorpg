package com.kingston.mmorpg.game.database.config;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 策划静态表db配置
 * 
 * @author kingston
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "configEntityManagerFactory",
	transactionManagerRef = "configDbTransactionManager", 
	basePackages = {"com.kingston.mmorpg.game.database.config" })
public class StaticDbConfig {

	@Autowired
	@Qualifier("configDataSource")
	private DataSource configDataSource;

	@Primary
	@Bean(name = "configEntityManager")
	public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
		return configEntityManagerFactory(builder).getObject().createEntityManager();
	}

	@Primary
	@Bean(name = "configEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean configEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(configDataSource).properties(getVendorProperties(configDataSource))
				.packages("com.kingston.mmorpg.game.database.config") // 设置实体类所在位置
				.persistenceUnit("primaryPersistenceUnit").build();
	}

	@Autowired
	private JpaProperties jpaProperties;

	private Map<String, String> getVendorProperties(DataSource dataSource) {
		return jpaProperties.getHibernateProperties(dataSource);
	}

	@Primary
	@Bean(name = "configDbTransactionManager")
	public PlatformTransactionManager configDbTransactionManager(EntityManagerFactoryBuilder builder) {
		return new JpaTransactionManager(configEntityManagerFactory(builder).getObject());
	}

}
