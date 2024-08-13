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
        // find annotated fields
        for (Field field : serviceObject.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                if (field.isAnnotationPresent(Qualifier.class)) {
                    Annotation annotation = field.getAnnotation(Qualifier.class);
                    // get the name of the Qualifier annotation
                    String className = ((Qualifier) annotation).name();
                    //get the object instance of this type
                    Object instance = fwContext.getServiceBeanWithName(className);
                    //do the injection
                    field.setAccessible(true);
                    field.set(serviceObject, instance);
                } else {
                    // get the type of the field
                    Class<?> theFieldType = field.getType();
                    //get the object instance of this type
                    Object instance = fwContext.getServiceBeanOftype(theFieldType);
                    //do the injection
                    field.setAccessible(true);
                    field.set(serviceObject, instance);
                }
            }
        }
        nextHandler.handle(serviceObject);

    }
}
