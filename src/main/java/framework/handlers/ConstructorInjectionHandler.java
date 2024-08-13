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
    public void handle(Object serviceObject) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        boolean isNewObject=false;
        Constructor[] constructors = serviceObject.getClass().getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                //found constructor
                Class<?>[] methodParameters = constructor.getParameterTypes();
                Class<?> parameterType = methodParameters[0];
                //get the object instance of this type
                Object parameterInstance = fwContext.getServiceBeanOftype(parameterType);
                //do the injection
                Object serviceClassInstance = (Object) constructor.newInstance(parameterInstance);
                fwContext.getServiceObjectMap().put(serviceClassInstance.getClass().getName(), serviceClassInstance);
                isNewObject=true;
                nextHandler.handle(serviceClassInstance);
            }
        }
        if (!isNewObject)
           nextHandler.handle(serviceObject);
    }
}
