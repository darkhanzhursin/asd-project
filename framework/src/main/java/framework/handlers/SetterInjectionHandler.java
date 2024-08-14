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
    public void handle(Object serviceObject) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Method[] methods = serviceObject.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Autowired.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Class<?> parameterType = parameterTypes[0];
                Object instance = fwContext.getServiceBeanOfType(parameterType);
                method.invoke(serviceObject, instance);
            }
        }
        nextHandler.handle(serviceObject);
    }
}
