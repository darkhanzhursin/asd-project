package framework.handlers;

import framework.FWContext;

import java.lang.reflect.InvocationTargetException;

public abstract class ServiceObjectHandler {
    protected ServiceObjectHandler nextHandler;
    protected FWContext fwContext;

    public ServiceObjectHandler(ServiceObjectHandler nextHandler, FWContext fwContext) {
        this.nextHandler = nextHandler;
        this.fwContext = fwContext;
    }

    public abstract void handle(Object serviceObject) throws IllegalAccessException, InvocationTargetException, InstantiationException;
}
