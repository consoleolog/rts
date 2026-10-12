package com.rts.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 루트 컨텍스트: DataSource, Liquibase, JPA, 트랜잭션, 서비스.
 *
 * <p>설정값은 classpath:rts.properties 를 기본으로 읽고, ${catalina.base}/conf/rts.properties 가 있으면 덮어쓴다.
 * 운영 DB 접속 정보는 WAR가 아니라 서버의 conf/rts.properties 에 둔다.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.rts")
@ComponentScan(basePackages = "com.rts",
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, ControllerAdvice.class}),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebConfig.class)
        })
@PropertySource(value = {"classpath:rts.properties", "file:${catalina.base:.}/conf/rts.properties"},
        ignoreResourceNotFound = true, encoding = "UTF-8")
public class AppConfig {

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource(Environment env) {
        HikariConfig config = new HikariConfig();
        config.setPoolName("rts");
        // Tomcat에서는 WAR 안의 드라이버를 DriverManager가 자동으로 찾지 못하므로 명시한다
        config.setDriverClassName(env.getProperty("db.driver", "com.mysql.cj.jdbc.Driver"));
        config.setJdbcUrl(env.getRequiredProperty("db.url"));
        config.setUsername(env.getRequiredProperty("db.username"));
        config.setPassword(env.getRequiredProperty("db.password"));
        config.setMaximumPoolSize(env.getProperty("db.pool.max-size", Integer.class, 10));
        config.setAutoCommit(false);
        return new HikariDataSource(config);
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource,
                                     @Value("${liquibase.enabled:true}") boolean enabled) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:db/changelog/db.changelog-master.xml");
        liquibase.setShouldRun(enabled);
        return liquibase;
    }

    /** Liquibase가 스키마를 맞춘 뒤에 Hibernate가 매핑을 검증하도록 순서를 고정한다. */
    @Bean
    @DependsOn("liquibase")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.rts");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties props = new Properties();
        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect"));
        // 스키마는 Liquibase로만 관리한다. Hibernate는 검증만 한다.
        props.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto", "validate"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql", "false"));
        props.setProperty("hibernate.format_sql", "true");
        props.setProperty("hibernate.connection.provider_disables_autocommit", "true");
        props.setProperty("hibernate.jdbc.time_zone", env.getProperty("hibernate.jdbc.time_zone", "Asia/Seoul"));
        emf.setJpaProperties(props);
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
