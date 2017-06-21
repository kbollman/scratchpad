package com.kevjim.common.config;


import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.HibernateTemplate;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
//@EnableEncryptableProperties
// This order is very critical, we want the settings an Operator can override to be read last
@PropertySource(name = "DeveloperProperties", value = "classpath:developer-intellij.properties", ignoreResourceNotFound = true)
@PropertySource(name = "ExternalProperties", value = "file:///opt/scratchpad/etc/scratchpad.local.properties", ignoreResourceNotFound = true)
public class DataSourceConfig {

    private static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
    @Autowired
    private Environment environment;
    /**
     * These will eventually be pulled in from file or Consul, but for dev simplicity, here for now.
     */
    private static final String KEY_ENTITIES_PKG = "com.kevjim.common.model";
    private static final String KEY_HIBERNATE_SHOW_SQL = "true";
    private static final String KEY_HIBERNATE_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
    private static final String KEY_DRIVER_CLASS = "org.postgresql.Driver";
    private static final String KEY_HOSTNAME = "localhost";
    private static final String KEY_DATABASE_NAME = "mydatabase";
    private static final String KEY_JDBC_URL = "jdbc:postgresql://" + KEY_HOSTNAME + ":5432/" + KEY_DATABASE_NAME;
    private static final String KEY_JDBC_USERNAME = "myuser";
    private static final String KEY_JDBC_PASSWORD = "mypassword";

    // Notice that the bean name is required, as jasypt-spring-boot detects custom String Encyptors by name
    // Leaving commented for now just to show how we could do a custom Encryptor if desired higher level of encryption
    /*
    @Bean(name = "jasyptStringEncryptor")
    public static StringEncryptor stringEncryptor() {
        logger.debug("In Bean creation for jasyptStringEncryptor");
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        //config.setPassword(environment.getProperty("trovata.security.key", "password"));
        config.setAlgorithm("PBEWithMD5AndTripleDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
    */

    public Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", KEY_HIBERNATE_DIALECT);
        properties.setProperty("hibernate.show_sql", KEY_HIBERNATE_SHOW_SQL);
        return properties;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(KEY_DRIVER_CLASS);
        dataSource.setUrl(KEY_JDBC_URL);
        dataSource.setUsername(KEY_JDBC_USERNAME);
        dataSource.setPassword(KEY_JDBC_PASSWORD);
        dataSource.setDefaultAutoCommit(Boolean.TRUE);
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean localSessionFactoryBean() {
        logger.debug("In Bean creation for LocalSessionFactoryBean");
        LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
        factory.setDataSource(dataSource());
        factory.setPackagesToScan(KEY_ENTITIES_PKG);
        factory.setHibernateProperties(hibernateProperties());
        return factory;
    }

    @Bean
    //@Autowired
    public HibernateTransactionManager transactionManager(
            SessionFactory sessionFactory) {
        logger.debug("In Bean creation for HibernateTransactionManager");
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean
    public HibernateTemplate hibernateTemplate(SessionFactory sessionFactory) {
        logger.debug("In Bean creation for HibernateTemplate");
        return new HibernateTemplate(sessionFactory);
    }
}
