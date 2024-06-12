package org.forfun.mmorpg.game.database.user;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 玩家动态表db配置
 * 
 *
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "userEntityManagerFactory", transactionManagerRef = "userTransactionManager", basePackages = {
		"org.forfun.mmorpg.game.database.user" })
public class UserDbConfig {

	@Autowired
	@Qualifier("userDataSource")
	private DataSource userDataSource;

	@Bean(name = "userEntityManager")
	public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
		return userEntityManagerFactory(builder).getObject().createEntityManager();
	}

	@Bean(name = "userEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean userEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(userDataSource).properties(getVendorProperties(userDataSource))
				.packages("org.forfun.mmorpg.game.database.user").persistenceUnit("userPersistenceUnit").build();
	}

	@Autowired
	private JpaProperties jpaProperties;

	private Map<String, String> getVendorProperties(DataSource dataSource) {
		return jpaProperties.getProperties();
	}

	@Bean(name = "userTransactionManager")
	public PlatformTransactionManager userTransactionManager(EntityManagerFactoryBuilder builder) {
		return new JpaTransactionManager(userEntityManagerFactory(builder).getObject());
	}

}
