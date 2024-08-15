package framework.events;

import java.lang.reflect.Method;

public class EventListenerMethod {
    private Object serviceObject;
    private Method listenerMethod;

    public EventListenerMethod(Object serviceObject, Method listenerMethod) {
        this.serviceObject = serviceObject;
        this.listenerMethod = listenerMethod;
    }

    public Object getServiceObject() {
        return serviceObject;
    }

    public Method getListenerMethod() {
        return listenerMethod;
    }
}
