package framework;

import framework.annotations.Async;
import framework.annotations.Profile;
import framework.annotations.Service;
import framework.handlers.HandlerFactory;
import framework.handlers.ServiceObjectHandler;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class FWContext {

    private static Map<String, Object> serviceObjectMap = new HashMap<>();
    private String activeProfile;
    EventContext eventContext = new EventContext();

    public FWContext() {

    }

    public void readServiceClasses() {
        try {
            Reflections reflections = new Reflections("application");
            // find and instantiate all classes annotated with the @Service annotation
            Set<Class<?>> servicetypes = reflections.getTypesAnnotatedWith(Service.class);
            for (Class<?> serviceClass : servicetypes) {
                serviceObjectMap.put(serviceClass.getName(), (Object) serviceClass.getDeclaredConstructor().newInstance());
            }
            // create a publisher in the context
            serviceObjectMap.put("publisher", new EventPublisher(eventContext));

            performContextSetup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void performContextSetup() {
        Properties properties = ConfigFileReader.getConfigProperties();
        activeProfile = properties.getProperty("activeprofile");
        ServiceObjectHandler handler= HandlerFactory.getChainHandler(this);
        try {
            for (Object serviceObject : serviceObjectMap.values()) {
                createAssyncProxy(serviceObject);
            }
            for (Object serviceObject : serviceObjectMap.values()) {
                handler.handle(serviceObject);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createAssyncProxy(Object serviceObject)  {
        Method[] methods = serviceObject.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Async.class)) {
                //found async method
                ClassLoader classLoader = serviceObject.getClass().getClassLoader();
                Class<?>[] interfaces = serviceObject.getClass().getInterfaces();
                AsyncProxy proxy = new AsyncProxy(serviceObject);
                Object serviceClassInstance = Proxy.newProxyInstance(classLoader, interfaces, proxy);
                getServiceObjectMap().put(serviceObject.getClass().getName(), serviceClassInstance);
            }
        }
    }

    public Object getServiceBeanOftype(Class interfaceClass) {
        // if the class has an interface
        List<Object> objectList = new ArrayList<Object>();
        try {
            for (Object theServiceClass : serviceObjectMap.values()) {
                Class<?>[] interfaces = theServiceClass.getClass().getInterfaces();

                for (Class<?> theInterface : interfaces) {
                    if (theInterface.getName().contentEquals(interfaceClass.getName()))
                        objectList.add(theServiceClass);
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
            for (Object theClass : serviceObjectMap.values()) {
                if (theClass.getClass().getName().equals(interfaceClass.getName()))
                    return theClass;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getServiceBeanWithName(String className) {
        return serviceObjectMap.get(className);
    }

    public static Map<String, Object> getServiceObjectMap() {
        return serviceObjectMap;
    }

    public EventContext getEventContext() {
        return eventContext;
    }
}
