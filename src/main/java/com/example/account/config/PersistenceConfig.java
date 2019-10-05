package com.example.account.config;

import com.example.account.audit.AuditorProvider;
import com.example.account.context.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.example.account.dao")
public class PersistenceConfig {

    private UserContext userContext;

    @Value("${bcrm.db.persistence.unit.name}")
    private String persistenceName;

    @Value("${bcrm.db.url}")
    private String url;

    @Value("${bcrm.db.username}")
    private String username;

    @Value("${bcrm.db.password}")
    private String password;

    @Value("${bcrm.db.hbm2ddl.auto}")
    private String hbm2ddlStrategy;

    @Value("${bcrm.db.driver}")
    private String driver;

    @Value("${bcrm.db.dialect}")
    private String dialect;

    @Value("${bcrm.db.naming.strategy}")
    private String namingStrategy;

    @Value("${bcrm.db.show.sql}")
    private String showSql;

    @Value("${bcrm.db.entity.package}")
    private String entityPackage;

    @Autowired
    public PersistenceConfig(UserContext userContext) {
        this.userContext = userContext;
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorProvider(userContext);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(entityPackage);

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setPersistenceUnitName(persistenceName);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();

        properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddlStrategy);
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.show_sql", showSql);

        return properties;
    }
}
