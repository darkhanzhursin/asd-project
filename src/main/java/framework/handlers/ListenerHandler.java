package framework.handlers;

import framework.FWContext;
import framework.annotations.EventListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ListenerHandler extends ServiceObjectHandler {


    public ListenerHandler(ServiceObjectHandler nextHandler, FWContext fwContext) {
        super(nextHandler, fwContext);
    }

    @Override
    public void handle(Object serviceObject) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Method[] methods = serviceObject.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(EventListener.class)) {
                //found event listener method
                Class<?>[] parameters = method.getParameterTypes();
                Class parameterClass = parameters[0];
                fwContext.getEventContext().addEventListeners(parameterClass.getName(),serviceObject, method);
            }
        }
        nextHandler.handle(serviceObject);
    }
}
