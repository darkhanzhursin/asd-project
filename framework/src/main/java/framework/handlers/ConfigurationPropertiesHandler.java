package framework.handlers;

import framework.ConfigFileReader;
import framework.FWContext;
import framework.annotations.ConfigurationProperties;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class ConfigurationPropertiesHandler extends ServiceObjectHandler {

    public ConfigurationPropertiesHandler(ServiceObjectHandler nextHandler, FWContext fwContext) {
        super(nextHandler, fwContext);
    }

    @Override
    public void handle(Object serviceObject) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Properties props = ConfigFileReader.readConfigFile();
        if (serviceObject.getClass().isAnnotationPresent(ConfigurationProperties.class)) {
            String prefix = serviceObject.getClass().getAnnotation(ConfigurationProperties.class).prefix();
            for (Field field : serviceObject.getClass().getDeclaredFields()) {
                Class<?> fieldType = field.getType();
                if (fieldType.getName().contentEquals("java.lang.String")) {
                    String toBeInjected = props.getProperty(prefix + "." + field.getName());
                    field.setAccessible(true);
                    field.set(serviceObject, toBeInjected);
                }
            }
        }
        nextHandler.handle(serviceObject);
    }
}
