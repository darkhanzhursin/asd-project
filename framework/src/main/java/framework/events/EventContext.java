package framework.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventContext {
    private static Map<String, List<EventListenerMethod>> listenerMap = new HashMap<>();

    public void registerListener(String eventType, Object object, Method method) {
        List<EventListenerMethod> events = listenerMap.get(eventType);
        if (events == null) {
            events = new ArrayList<>();
        }
        events.add(new EventListenerMethod(object, method));
        listenerMap.put(eventType, events);
    }

    public void publish(Object eventObject) throws InvocationTargetException, IllegalAccessException {
        System.out.println("***************eventObject.getClass().getName() ="+eventObject.getClass().getName());
        List<EventListenerMethod> eventList = listenerMap.get(eventObject.getClass().getName());
        for (EventListenerMethod eventListenerMethod : eventList) {
            eventListenerMethod.getListenerMethod().invoke(eventListenerMethod.getServiceObject(), eventObject);
        }
    }
}
