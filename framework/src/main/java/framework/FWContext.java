package framework;

import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class FWContext {

    private static List<Object> serviceObjectList = new ArrayList<>();
    private String activeProfile;

    public void start(Class<?> clazz) {
        try {
            Reflections reflections = new Reflections(clazz.getPackageName());
            scannAndInstatiateServiceClasses(reflections);
            performDI(clazz);
            performContextSetup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scannAndInstatiateServiceClasses(Reflections reflections) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // find and instantiate all classes annotated with the @Service annotation
        Set<Class<?>> servicetypes = reflections.getTypesAnnotatedWith(Service.class);
        for (Class<?> serviceClass : servicetypes) {
            serviceObjectList.add(serviceClass.getDeclaredConstructor().newInstance());
        }
    }

    private void performDI(Class<?> applicationClass) {
        try {
//            // create instance of the application class
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

    private Object getServiceBeanOfType(Class interfaceClass) {
        Object service = null;
        try {
            for (Object theClass : serviceObjectList) {
                Class<?>[] interfaces = theClass.getClass().getInterfaces();

                for (Class<?> theInterface : interfaces) {
                    if (theInterface.getName().contentEquals(interfaceClass.getName()))
                        service = theClass;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return service;
    }

    private void performContextSetup() {
        Properties properties = ConfigFileReader.readConfigFile();
        activeProfile = properties.getProperty("activeprofile");
//        ServiceObjectHandler handler= HandlerFactory.getChainHandler(this);
//        try {
//            for (Object serviceObject : serviceObjectMap.values()) {
//                createAssyncProxy(serviceObject);
//            }
//            for (Object serviceObject : serviceObjectMap.values()) {
//                handler.handle(serviceObject);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
