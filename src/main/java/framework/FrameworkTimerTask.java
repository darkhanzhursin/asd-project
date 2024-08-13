package framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.TimerTask;

public class FrameworkTimerTask extends TimerTask {

    private Object serviceObject;
    private Method scheduledMethod;

    public FrameworkTimerTask(Object serviceObject, Method scheduledMethod) {
        this.serviceObject = serviceObject;
        this.scheduledMethod = scheduledMethod;
    }

    public void run() {
        try {
            scheduledMethod.invoke(serviceObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}