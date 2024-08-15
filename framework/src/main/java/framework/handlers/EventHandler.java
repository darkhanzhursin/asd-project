package framework.handlers;

import framework.FWContext;
import framework.annotations.EventListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventHandler extends ServiceObjectHandler {

    public EventHandler(ServiceObjectHandler nextHandler, FWContext fwContext) {
        super(nextHandler, fwContext);
    }

    @Override
    public void handle(Object serviceObject) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Method[] methods = serviceObject.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(EventListener.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Class<?> eventClass = parameterTypes[0];
                fwContext.getEventContext().registerListener(eventClass.getName(), serviceObject, method);
            }
        }
        nextHandler.handle(serviceObject);
    }
}
