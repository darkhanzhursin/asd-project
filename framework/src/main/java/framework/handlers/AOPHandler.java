package framework.handlers;

import framework.FWContext;
import framework.annotations.After;
import framework.annotations.Before;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AOPHandler {

    private final Map<String, Method> beforeMethods = new HashMap<>();
    private final Map<String, Method> afterMethods = new HashMap<>();

    public AOPHandler(Object aspect) {
        for (Method method : aspect.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                Before before = method.getAnnotation(Before.class);
                beforeMethods.put(before.pointcut(), method);
            }
            if (method.isAnnotationPresent(After.class)) {
                After after = method.getAnnotation(After.class);
                afterMethods.put(after.pointcut(), method);
            }
        }
    }

    public void handleBefore(String pointcut, Object... args) throws InvocationTargetException, IllegalAccessException {
        Method method = beforeMethods.get(pointcut);
        if (method != null) {
            method.invoke(null, null);
        }
    }

    public void handleAfter(String pointcut, Object... args) throws InvocationTargetException, IllegalAccessException {
        Method method = afterMethods.get(pointcut);
        if (method != null) {
            method.invoke(null, null);
        }
    }
}
