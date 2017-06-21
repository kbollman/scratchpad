package com.kevjim.prototype.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Abstract DAO
 */
public abstract class AbstractDAO {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected HibernateTemplate hibernateTemplate;
    protected TransactionTemplate transactionTemplate;

    public void setHibernateTransactionManager(HibernateTransactionManager hibernateTransactionManager) {
        this.transactionTemplate = new TransactionTemplate(hibernateTransactionManager);
    }

    public void setLocalSessionFactoryBean(LocalSessionFactoryBean localSessionFactoryBean) {
        this.hibernateTemplate = new HibernateTemplate(localSessionFactoryBean.getObject());
    }
}
