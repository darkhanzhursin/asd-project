package framework.handlers;

import framework.FWContext;
import framework.FrameworkTimerTask;
import framework.annotations.Scheduled;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;

public class ScheduledMethodsHandler extends ServiceObjectHandler {


    public ScheduledMethodsHandler(ServiceObjectHandler nextHandler, FWContext fwContext) {
        super(nextHandler, fwContext);
    }

    @Override
    public void handle(Object serviceObject) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Method[] methods = serviceObject.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Scheduled.class)) {
                //found scheduled method
                //get the fixedRate
                Annotation annotation = method.getAnnotation(Scheduled.class);
                // get the name of the Qualifier annotation
                int rate = ((Scheduled) annotation).fixedRate();
                String cron = ((Scheduled) annotation).cron();

                Timer timer = new Timer();
                if (rate > 0)
                    timer.scheduleAtFixedRate(new FrameworkTimerTask(serviceObject, method), 0, rate);

                if (cron != "") {
                    int cronrate = getCronRate(cron);
                    if (cronrate > 0)
                        timer.scheduleAtFixedRate(new FrameworkTimerTask(serviceObject, method), 0, cronrate);
                }
            }
        }
        nextHandler.handle(serviceObject);
    }

    public int getCronRate(String cron) {
        String[] splitresult = cron.split(" ");
        String secondsString = splitresult[0];
        String minutesString = splitresult[1];
        int seconds = Integer.parseInt(secondsString);
        int minutes = Integer.parseInt(minutesString);
        return (minutes * 60 + seconds) *1000;
    }
}
