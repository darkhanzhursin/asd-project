package framework;

import framework.annotations.Autowired;

import java.lang.reflect.Field;

public class SpringFramework {
    public static void run(Class applicationClass) {
        // create the context
        FWContext fWContext = new FWContext();
        fWContext.readServiceClasses();
        try {
            // create instance of the application class
            Object applicationObject = (Object) applicationClass.getDeclaredConstructor().newInstance();
            // find annotated fields
            for (Field field : applicationObject.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    // get the type of the field
                    Class<?> theFieldType = field.getType();
                    // get the object instance of this type
                    Object instance = fWContext.getServiceBeanOftype(theFieldType);
                    // do the injection
                    field.setAccessible(true);
                    field.set(applicationObject, instance);
                }
            }
            //call the run() method
            if (applicationObject instanceof Runnable)
                ((Runnable)applicationObject).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
