package framework.handlers;

import framework.FWContext;
import framework.annotations.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class FieldInjectionHandler extends ServiceObjectHandler {

    public FieldInjectionHandler(ServiceObjectHandler nextHandler, FWContext fwContext) {
        super(nextHandler, fwContext);
    }

    @Override
    public void handle(Object serviceObject) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        for (Field field : serviceObject.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Class<?> fieldType = field.getType();
                Object instance = fwContext.getServiceBeanOfType(fieldType);
                field.setAccessible(true);
                field.set(serviceObject, instance);
            }
        }
        nextHandler.handle(serviceObject);
    }
}
