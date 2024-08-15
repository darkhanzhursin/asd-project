package framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.TimerTask;

public class FWTimerTask extends TimerTask {

    private Object serviceObject;
    private Method scheduledMethod;

    public FWTimerTask(Object serviceObject, Method scheduledMethod) {
        this.serviceObject = serviceObject;
        this.scheduledMethod = scheduledMethod;
    }

    @Override
    public void run() {
        try {
            scheduledMethod.invoke(serviceObject);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
