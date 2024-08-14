package framework;

import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FWContext {

    private static List<Object> componentObjectList = new ArrayList<>();
    private static List<Object> serviceObjectList = new ArrayList<>();

    public void start(Class<?> clazz) {
        try {
            Reflections reflections = new Reflections(clazz.getPackageName());
            scannAndInstatiateServiceClasses(reflections);
            scannAndInstatiateComponentClasses(reflections);
            performDI();

            for (Object serviceObject : componentObjectList) {
                for (Method method : serviceObject.getClass().getDeclaredMethods()) {
                    method.invoke(serviceObject);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scannAndInstatiateComponentClasses(Reflections reflections) throws InstantiationException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // find and instantiate all classes annotated with the @Component annotation
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(Component.class);
        for (Class<?> implementationClass : types) {
            componentObjectList.add(implementationClass.getDeclaredConstructor().newInstance());
        }
    }

    private void scannAndInstatiateServiceClasses(Reflections reflections) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // find and instantiate all classes annotated with the @Service annotation
        Set<Class<?>> servicetypes = reflections.getTypesAnnotatedWith(Service.class);
        for (Class<?> serviceClass : servicetypes) {
            serviceObjectList.add(serviceClass.getDeclaredConstructor().newInstance());
        }
    }

    private void performDI() {
        try {
            for (Object componentObject : componentObjectList) {
                // for fields
                for (Field field : componentObject.getClass().getDeclaredFields()) {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        // get the type of the field
                        Class<?> theFieldType =field.getType();
                        //get the object instance of this type
                        Object instance = getServiceBeanOfType(theFieldType);
                        //do the injection
                        field.setAccessible(true);
                        field.set(componentObject, instance);
                    }
                }
                // TODO
                //for methods
                //for constructors
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getServiceBeanOfType(Class interfaceClass) {
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
}
