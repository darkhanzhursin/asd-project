package framework.events;

import java.lang.reflect.InvocationTargetException;

public class EventPublisher {
    private EventContext eventContext;

    public EventPublisher(EventContext eventContext) {
        this.eventContext = eventContext;
    }

    public void publish(Object event) throws InvocationTargetException, IllegalAccessException {
        eventContext.publish(event);
    }
}
