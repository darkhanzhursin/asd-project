package framework;

import java.lang.reflect.Field;

public class FWApplication {

    public static void run(Class applicationClass) {
        // create the context
        FWContext fwContext = new FWContext();

        try {
            // create instance of the application class
            Object applicationObject = applicationClass.getDeclaredConstructor().newInstance();
            // find annotated fields
            for (Field field : applicationObject.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    // get the type of the field
                    Class<?> fieldType = field.getType();
                    // get the object instance of this type
                    Object instance = fwContext.getServiceBeanOfType(fieldType);
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
}
