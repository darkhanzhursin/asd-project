package framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventContext {
    private static Map<String, List<EventListenerMethod>> eventListenerMap = new HashMap<>();

    public void addEventListeners(String eventType, Object object, Method method ){
        List<EventListenerMethod> eventList = eventListenerMap.get(eventType);
        if (eventList == null) {
            eventList = new ArrayList<>();
        }
        eventList.add(new EventListenerMethod(object, method));
        eventListenerMap.put(eventType, eventList);
    }

    public void publish(Object eventObject) throws InvocationTargetException, IllegalAccessException {
        System.out.println("***************eventObject.getClass().getName() ="+eventObject.getClass().getName());
        List<EventListenerMethod> eventList = eventListenerMap.get(eventObject.getClass().getName());
        for (EventListenerMethod eventListenerMethod : eventList) {
            eventListenerMethod.getListenerMethod().invoke(eventListenerMethod.getServiceObject(), eventObject);
        }
    }
}

