package framework;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FWContext {

    private static List<Object> serviceObjectList = new ArrayList<>();

    public void start(Class<?> clazz) {
        try {
            Reflections reflections = new Reflections(clazz.getPackageName());
            scannAndInstatiateServiceClasses(reflections);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scannAndInstatiateServiceClasses(Reflections reflections) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // find and instantiate all classes annotated with the @Service annotation
        Set<Class<?>> servicetypes = reflections.getTypesAnnotatedWith(Service.class);
        for (Class<?> serviceClass : servicetypes) {
            serviceObjectList.add((Object) serviceClass.getDeclaredConstructor().newInstance());
        }
    }
}
