package framework;

import framework.annotation.Autowired;
import framework.annotation.Component;
import framework.annotation.Service;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
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
                    if (method.getParameterCount() == 0) {
                        // No-arg method, invoke directly
                        method.invoke(serviceObject);
                    } else {
                        // Resolve parameters
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        Object[] parameters = new Object[parameterTypes.length];

                        for (int i = 0; i < parameterTypes.length; i++) {
                            parameters[i] = getServiceBeanOftype(parameterTypes[i]);
                        }

                        // Invoke method with resolved parameters
                        method.invoke(serviceObject, parameters);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void scannAndInstatiateComponentClasses(Reflections reflections) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(Component.class);
        for (Class<?> implementationClass : types) {
            Constructor<?>[] constructors = implementationClass.getDeclaredConstructors();
            Object instance = null;

            for (Constructor<?> constructor : constructors) {
                if (constructor.isAnnotationPresent(Autowired.class)) {
                    Class<?>[] parameterTypes = constructor.getParameterTypes();
                    Object[] parameters = new Object[parameterTypes.length];

                    for (int i = 0; i < parameterTypes.length; i++) {
                        parameters[i] = getServiceBeanOftype(parameterTypes[i]);
                    }

                    instance = constructor.newInstance(parameters);
                    break;
                }
            }

            if (instance == null) {
                instance = implementationClass.getDeclaredConstructor().newInstance();
            }

            componentObjectList.add(instance);
        }
    }


    private void scannAndInstatiateServiceClasses(Reflections reflections) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Set<Class<?>> servicetypes = reflections.getTypesAnnotatedWith(Service.class);
        for (Class<?> serviceClass : servicetypes) {
            Constructor<?>[] constructors = serviceClass.getDeclaredConstructors();
            Object instance = null;

            for (Constructor<?> constructor : constructors) {
                if (constructor.isAnnotationPresent(Autowired.class)) {
                    Class<?>[] parameterTypes = constructor.getParameterTypes();
                    Object[] parameters = new Object[parameterTypes.length];

                    for (int i = 0; i < parameterTypes.length; i++) {
                        parameters[i] = getServiceBeanOftype(parameterTypes[i]);
                    }

                    instance = constructor.newInstance(parameters);
                    break;
                }
            }

            if (instance == null) {
                instance = serviceClass.getDeclaredConstructor().newInstance();
            }

            serviceObjectList.add(instance);
        }
    }


    private void performDI() {
        try {
            for (Object componentObject : componentObjectList) {
                // Field injection
                for (Field field : componentObject.getClass().getDeclaredFields()) {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        Class<?> theFieldType = field.getType();
                        Object instance = getServiceBeanOftype(theFieldType);
                        field.setAccessible(true);
                        field.set(componentObject, instance);
                    }
                }

                // Setter injection
                for (Method method : componentObject.getClass().getDeclaredMethods()) {
                    if (method.isAnnotationPresent(Autowired.class) && method.getParameterCount() == 1) {
                        Class<?> parameterType = method.getParameterTypes()[0];
                        Object instance = getServiceBeanOftype(parameterType);
                        method.setAccessible(true);
                        method.invoke(componentObject, instance);
                    }
                }

                // Constructor injection
                for (Constructor<?> constructor : componentObject.getClass().getDeclaredConstructors()) {
                    if (constructor.isAnnotationPresent(Autowired.class)) {
                        Class<?>[] parameterTypes = constructor.getParameterTypes();
                        Object[] instances = new Object[parameterTypes.length];
                        for (int i = 0; i < parameterTypes.length; i++) {
                            instances[i] = getServiceBeanOftype(parameterTypes[i]);
                        }
                        constructor.setAccessible(true);
                        Object newInstance = constructor.newInstance(instances);
                        // Replace the object in the componentObjectList
                        componentObjectList.remove(componentObject);
                        componentObjectList.add(newInstance);
                        break;
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    private Object getServiceBeanOftype(Class<?> interfaceClass) {
        Object service = null;
        try {
            for (Object theClass : serviceObjectList) {
                Class<?>[] interfaces = theClass.getClass().getInterfaces();

                for (Class<?> theInterface : interfaces) {
                    if (theInterface.getName().contentEquals(interfaceClass.getName())) {
                        service = theClass;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return service;
    }
}
