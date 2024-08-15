package framework;

import framework.annotations.Async;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class AsyncProxy implements InvocationHandler {

    private Object targetObject;

    public AsyncProxy(Object target) {
        this.targetObject = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method targetMethod= null;
        Method[] methods = targetObject.getClass().getDeclaredMethods();
        for (Method theMethod : methods){
            if (theMethod.getName().equals(method.getName())){
                targetMethod= theMethod;
            }
        }
        if (targetMethod != null && targetMethod.isAnnotationPresent(Async.class)) {
            System.out.println("Executing method asynchronously: " + method.getName());
            CompletableFuture.runAsync(() -> {
                try {
                    method.invoke(targetObject, args);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }, Executors.newCachedThreadPool());
            return null;
        } else {
            System.out.println("Executing method synchronously: " + method.getName());
            return method.invoke(targetObject, args);
        }
    }

}
