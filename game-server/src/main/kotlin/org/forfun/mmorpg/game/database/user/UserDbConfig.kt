package org.forfun.mmorpg.game.database.user

import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

/**
 * 玩家动态表db配置
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "userEntityManagerFactory",
    transactionManagerRef = "userTransactionManager",
    basePackages = ["org.forfun.mmorpg.game.database.user"]
)
open class UserDbConfig {

    @Autowired
    @Qualifier("userDataSource")
    private lateinit var userDataSource: DataSource

    @Bean(name = ["userEntityManager"])
    open fun entityManager(builder: EntityManagerFactoryBuilder): EntityManager {
        return userEntityManagerFactory(builder).`object`!!.createEntityManager()
    }

    @Bean(name = ["userEntityManagerFactory"])
    open fun userEntityManagerFactory(builder: EntityManagerFactoryBuilder): LocalContainerEntityManagerFactoryBean {
        return builder.dataSource(userDataSource)
            .properties(getVendorProperties(userDataSource))
            .packages("org.forfun.mmorpg.game.database.user")
            .persistenceUnit("userPersistenceUnit")
            .build()
    }

    @Autowired
    private lateinit var jpaProperties: JpaProperties

    private fun getVendorProperties(dataSource: DataSource): Map<String, String> {
        return jpaProperties.properties
    }

    @Bean(name = ["userTransactionManager"])
    open fun userTransactionManager(builder: EntityManagerFactoryBuilder): PlatformTransactionManager {
        return JpaTransactionManager(userEntityManagerFactory(builder).`object`)
    }
}
