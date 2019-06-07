package com.thinkstep.test.onlineusers.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.ManagedBean;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Collections;

//@Configurable
//@EnableJpaRepositories(
//        entityManagerFactoryRef = "localEntityManagerFactory",
//        transactionManagerRef = "localTransactionManager",
//        basePackages = {
//                "com.thinkstep.test.onlineusers.log.repository"
//        }
//)
//@EnableTransactionManagement
//@Component
public class H2Config {

    @Value("${spring.datasourcelocal.username}")
    private String username;

    @Value("${spring.datasourcelocal.password}")
    private String password;

    @Value("${spring.datasourcelocal.url}")
    private String jdbcUrl;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Bean(name = "localDS")
    @ConfigurationProperties(prefix = "spring.datasourcelocal")
    public DataSource inMemoryDs(){
        return DataSourceBuilder
                .create()
                .url(jdbcUrl)
                .username(username)
                .password(password)
                .build();
    }

    @Bean(name="localEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean defaultEntityManagerFactory(
            final EntityManagerFactoryBuilder builder,
            final @Qualifier("localDS") DataSource ds) {

        return builder.dataSource(ds)
                .packages("com.thinkstep.test.onlineusers.metrics")
                .properties(Collections.singletonMap("hibernate.hbm2ddl.auto", ddlAuto))
                .build();
    }

    @Bean(name="localTransactionManager")
    public PlatformTransactionManager defaultTransactionManager(@Qualifier("localEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
