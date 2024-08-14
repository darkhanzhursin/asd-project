package framework.handlers;

import framework.ConfigFileReader;
import framework.FWContext;
import framework.annotations.Value;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class ValueInjectionHandler extends ServiceObjectHandler {


    public ValueInjectionHandler(ServiceObjectHandler nextHandler, FWContext fwContext) {
        super(nextHandler, fwContext);
    }

    @Override
    public void handle(Object serviceObject) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Properties props = ConfigFileReader.readConfigFile();
        for (Field field : serviceObject.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Value.class)) {
                Class<?> fieldType = field.getType();
                if (fieldType.getName().contentEquals("java.lang.String")) {
                    String value = field.getAnnotation(Value.class).name();
                    String toBeInjected = props.getProperty(value);
                    field.setAccessible(true);
                    field.set(serviceObject, toBeInjected);
                }
            }
        }
        nextHandler.handle(serviceObject);
    }
}
