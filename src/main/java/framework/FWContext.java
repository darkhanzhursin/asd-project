package framework;

import org.reflections.Reflections;

public class FWContext {

    public void start(Class<?> clazz) {
        try {
            Reflections reflections = new Reflections(clazz.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
