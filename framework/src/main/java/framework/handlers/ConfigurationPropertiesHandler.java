package framework.handlers;

import framework.FWContext;

import java.lang.reflect.InvocationTargetException;

public class ConfigurationPropertiesHandler extends ServiceObjectHandler {

    public ConfigurationPropertiesHandler(ServiceObjectHandler nextHandler, FWContext fwContext) {
        super(nextHandler, fwContext);
    }

    @Override
    public void handle(Object serviceObject) throws IllegalAccessException, InvocationTargetException, InstantiationException {

    }
}
