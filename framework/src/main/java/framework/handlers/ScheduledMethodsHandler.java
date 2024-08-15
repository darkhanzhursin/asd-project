package framework.handlers;

import framework.FWContext;
import framework.FWTimerTask;
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
                Annotation annotation = method.getAnnotation(Scheduled.class);
                int rate = ((Scheduled) annotation).fixedRate();
                String cron = ((Scheduled) annotation).cron();

                Timer timer = new Timer();
                if (rate > 0) {
                    timer.scheduleAtFixedRate(new FWTimerTask(serviceObject, method), 0, rate);
                }

                if (cron != "") {
                    int cronRate = getCronRate(cron);
                    if (cronRate > 0) {
                        timer.scheduleAtFixedRate(new FWTimerTask(serviceObject, method), 0, cronRate);
                    }
                }
            }
        }
        nextHandler.handle(serviceObject);
    }

    private int getCronRate(String cron) {
        String[] splitResult = cron.split(" ");
        String secondsStr = splitResult[0];
        String minutesStr = splitResult[1];
        int seconds = Integer.parseInt(secondsStr);
        int minutes = Integer.parseInt(minutesStr);
        return (minutes * 60 + seconds) * 1000;
    }
}
