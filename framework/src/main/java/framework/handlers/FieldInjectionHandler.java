package framework.handlers;

import framework.FWContext;
import framework.annotations.Autowired;
import framework.annotations.Qualifier;

import java.lang.annotation.Annotation;
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
                if (field.isAnnotationPresent(Qualifier.class)) {
                    Annotation qualifier = field.getAnnotation(Qualifier.class);
                    String className = ((Qualifier) qualifier).name();
                    Object instance = fwContext.getServiceBeanWithName(className);
                    field.setAccessible(true);
                    field.set(serviceObject, instance);
                } else {
                    Class<?> fieldType = field.getType();
                    Object instance = fwContext.getServiceBeanOfType(fieldType);
                    field.setAccessible(true);
                    field.set(serviceObject, instance);
                }
            }
        }
        nextHandler.handle(serviceObject);
    }
}
