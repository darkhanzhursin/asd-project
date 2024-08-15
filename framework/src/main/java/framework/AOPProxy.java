package framework;

import framework.annotations.Before;
import framework.handlers.AOPHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AOPProxy implements InvocationHandler {

    private Object target;

    public AOPProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method targetMethod = null;
        Method[] methods = target.getClass().getDeclaredMethods();
        for (Method theMethod : methods) {
            if (theMethod.getName().equals(method.getName())) {
                targetMethod = theMethod;
            }
        }

        if (targetMethod != null && targetMethod.isAnnotationPresent(Before.class)) {
            Before before = targetMethod.getAnnotation(Before.class);

        }
        return null;
    }

    public static <T> T createProxy(T target, AOPHandler handler) {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    String pointcut = target.getClass().getName() + "." + method.getName();

                    handler.handleBefore(pointcut, args);
                    Object result = method.invoke(target, args);
                    handler.handleAfter(pointcut, args);

                    return result;
                });
    }
}
