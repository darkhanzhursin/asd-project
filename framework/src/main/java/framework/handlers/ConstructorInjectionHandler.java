package framework.handlers;

import framework.FWContext;
import framework.annotations.Autowired;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConstructorInjectionHandler extends ServiceObjectHandler {

    public ConstructorInjectionHandler(ServiceObjectHandler nextHandler, FWContext fwContext) {
        super(nextHandler, fwContext);
    }

    @Override
    public void handle(Object serviceObject) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        boolean isNewObject = false;
        Constructor[] constructors = serviceObject.getClass().getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Class<?> parameterType = parameterTypes[0];
                Object instance = fwContext.getServiceBeanOfType(parameterType);
                // do the injection
                Object serviceInstance = constructor.newInstance(instance);
                fwContext.getServiceObjectList().add(serviceInstance);
                isNewObject = true;
                nextHandler.handle(serviceInstance);
            }
        }
        if (!isNewObject) {
            nextHandler.handle(serviceObject);
        }
    }
}