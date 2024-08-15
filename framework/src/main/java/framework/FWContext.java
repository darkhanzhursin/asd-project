package framework;

import framework.annotations.Autowired;
import framework.annotations.Profile;
import framework.annotations.Service;
import framework.events.EventContext;
import framework.events.EventPublisher;
import framework.handlers.ServiceObjectHandler;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class FWContext {

    private static Map<String, Object> serviceObjectMap = new HashMap<>();
    private String activeProfile;
    EventContext eventContext = new EventContext();

    public void start(Class<?> clazz) {
        try {
            Reflections reflections = new Reflections(clazz.getPackageName());
            scannAndInstatiateServiceClasses(reflections);
            performDI(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scannAndInstatiateServiceClasses(Reflections reflections) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // find and instantiate all classes annotated with the @Service annotation
        Set<Class<?>> servicetypes = reflections.getTypesAnnotatedWith(Service.class);
        for (Class<?> serviceClass : servicetypes) {
            serviceObjectMap.put(serviceClass.getName(), serviceClass.getDeclaredConstructor().newInstance());
        }
        serviceObjectMap.put("publisher", new EventPublisher(eventContext));
        performContextSetup();
    }

    private void performDI(Class<?> applicationClass) {
        try {
            // create instance of the application class
            Object applicationObject = applicationClass.getDeclaredConstructor().newInstance();
            // find annotated fields
            for (Field field : applicationObject.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    // get the type of the field
                    Class<?> fieldType = field.getType();
                    // get the object instance of this type
                    Object instance = getServiceBeanOfType(fieldType);
                    // do the injection
                    field.setAccessible(true);
                    field.set(applicationObject, instance);
                }
            }
            // call the run() method
            if (applicationObject instanceof Runnable) {
                ((Runnable) applicationObject).run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getServiceBeanOfType(Class interfaceClass) {
        List<Object> objectList = new ArrayList<>();
        try {
            for (Object theClass : serviceObjectMap.values()) {
                Class<?>[] interfaces = theClass.getClass().getInterfaces();

                for (Class<?> theInterface : interfaces) {
                    if (theInterface.getName().contentEquals(interfaceClass.getName()))
                       objectList.add(theClass);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (objectList.size() == 1) return objectList.get(0);
        if (objectList.size() > 1) {
            for (Object theObject : objectList) {
                String profilevalue = theObject.getClass().getAnnotation(Profile.class).value();
                if (profilevalue.contentEquals(activeProfile)) {
                    return theObject;
                }
            }
        }

        // if the class has no interface
        try {
            for (Object instance : serviceObjectMap.values()) {
                if (instance.getClass().getName().contentEquals(interfaceClass.getName()))
                    return instance;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void performContextSetup() {
        Properties properties = ConfigFileReader.readConfigFile();
        activeProfile = properties.getProperty("activeprofile");
        ServiceObjectHandler handler= Handler.getChainHandler(this);
        try {
            for (Object serviceObject : serviceObjectMap.values()) {
                handler.handle(serviceObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> getServiceObjectMap() {
        return serviceObjectMap;
    }

    public Object getServiceBeanWithName(String className) {
        return serviceObjectMap.get(className);
    }

    public EventContext getEventContext() {
        return eventContext;
    }
}
