package com.kevjim.prototype.config;

import com.kevjim.common.config.DataSourceConfig;
import com.kevjim.prototype.controller.ProtoController;
import com.kevjim.prototype.data.AbstractDAO;
import com.kevjim.prototype.data.MyTableDAO;
import com.kevjim.prototype.data.MyTableDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataSourceConfig.class)
public class AppConfig implements ApplicationContextAware {
    @Autowired
    DataSourceConfig dataSourceConfig;

    private static Logger logger = LoggerFactory.getLogger(AppConfig.class);
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public MyTableDAO myTableDAO() {
        logger.debug("In Bean creation for myTableDAO");
        return createDAO(MyTableDAOImpl.class);
    }

    @Bean
    public ProtoController protoController() {
        logger.debug("In Bean creation for protoController");
        return new ProtoController(myTableDAO());
    }

    /**
     * Helper method to create DAO beans.  Note that bean-wise this follows the construct-set pattern rather than the construct-only pattern
     * (we can't use a constructor because AbstractDAO is . . . abstract, which was needed to avoid duplicating constructors in all impl classes).
     * This method abstracts away the "set" to keep the bean accessors clean.
     *
     * @param daoClass The DAO class for which to create an intialized instance
     * @param <T>      The Type of DAO class to create and initialize
     * @return The initialized DAO instance
     */
    private <T extends AbstractDAO> T createDAO(Class<T> daoClass) {
        T daoImpl = null;
        try {
            daoImpl = daoClass.newInstance();
            daoImpl.setHibernateTransactionManager(dataSourceConfig.transactionManager(dataSourceConfig.localSessionFactoryBean().getObject()));
            daoImpl.setLocalSessionFactoryBean(dataSourceConfig.localSessionFactoryBean());
        } catch (Throwable t) {
            // fall through
        }
        return daoImpl;
    }
}



