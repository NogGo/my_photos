package ru.myphotos.common.producer;

import ru.myphotos.common.annotation.cdi.PropertiesSource;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.Properties;

@Dependent
public class PropertiesSourceProducer extends AbstractPropertiesLoader {

    @Produces
    @PropertiesSource("")
    private Properties loadProperties(InjectionPoint injectionPoint) {
        Properties properties = new Properties();
        PropertiesSource propertiesSource = injectionPoint.getAnnotated().getAnnotation(PropertiesSource.class);
        loadProperties(properties, propertiesSource.value());
        return properties;
    }
}