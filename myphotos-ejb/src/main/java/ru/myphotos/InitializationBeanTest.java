package ru.myphotos;

import ru.myphotos.common.annotation.cdi.PropertiesSource;
import ru.myphotos.common.annotation.cdi.Property;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Startup
@Singleton
public class InitializationBeanTest {

    @Inject
    @Property("myphotos.host")
    private String host;

    @Inject
    @PropertiesSource("classpath:application.properties")
    private Properties properties;

    @Inject
    protected Logger logger;

    @PostConstruct
    public void init() {
        logger.log(Level.INFO, "----------- host= {0}", host);
        logger.log(Level.INFO, "----------- properties= {0}", properties);
    }
}
