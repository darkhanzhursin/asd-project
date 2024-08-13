package framework;


import java.lang.reflect.InvocationTargetException;

public class EventPublisher {
    private  EventContext eventContext;

    public EventPublisher(EventContext eventContext) {
        this.eventContext = eventContext;
    }

    public void publish(Object eventObject) throws InvocationTargetException, IllegalAccessException {
        eventContext.publish(eventObject);
    }
}
