package framework.handlers;

import framework.FWContext;
import framework.annotations.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SetterInjectionHandler extends ServiceObjectHandler {


    public SetterInjectionHandler(ServiceObjectHandler nextHandler, FWContext fwContext) {
        super(nextHandler, fwContext);
    }

    @Override
    public void handle(Object serviceObject) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Method[] methods = serviceObject.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Autowired.class)) {
                //found setter method
                Class<?>[] methodParameters = method.getParameterTypes();
                Class<?> parameterType = methodParameters[0];
                //get the object instance of this type
                Object instance = fwContext.getServiceBeanOftype(parameterType);
                //do the injection
                method.invoke(serviceObject, instance);
            }
        }
        nextHandler.handle(serviceObject);
    }
}
