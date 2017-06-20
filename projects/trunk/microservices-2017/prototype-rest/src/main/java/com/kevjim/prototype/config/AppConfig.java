package com.kevjim.prototype.config;

import com.kevjim.prototype.controller.ProtoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(AppConfig.class);
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public ProtoController protoController() {
        logger.debug("In Bean creation for protoController");
        return new ProtoController();
    }

}



