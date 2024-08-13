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
        Properties properties = ConfigFileReader.getConfigProperties();
        // find annotated fields
        for (Field field : serviceObject.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Value.class)) {
                // get the type of the field
                Class<?> theFieldType = field.getType();
                if (field.getType().getName().contentEquals("java.lang.String")) {
                    // get attribute value
                    String attrValue = field.getAnnotation(Value.class).name();
                    // get the property value
                    String toBeInjectedString = properties.getProperty(attrValue);
                    // do the injection
                    field.setAccessible(true);
                    field.set(serviceObject, toBeInjectedString);
                }
            }
        }
        nextHandler.handle(serviceObject);
    }
}
