package framework;

import framework.handlers.*;

public class Handler {

    public static ServiceObjectHandler getChainHandler(FWContext fwContext) {
        LastHandler lastHandler = new LastHandler(null, fwContext);
        SetterInjectionHandler setterInjectionHandler = new SetterInjectionHandler(lastHandler, fwContext);
        FieldInjectionHandler fieldInjectionHandler = new FieldInjectionHandler(setterInjectionHandler, fwContext);
        ConstructorInjectionHandler constructorInjectionHandler = new ConstructorInjectionHandler(fieldInjectionHandler, fwContext);
        return constructorInjectionHandler;
    }
}
