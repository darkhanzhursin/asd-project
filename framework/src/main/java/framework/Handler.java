package framework;

import framework.handlers.*;

public class Handler {

    public static ServiceObjectHandler getChainHandler(FWContext fwContext) {
        LastHandler lastHandler = new LastHandler(null, fwContext);
        ValueInjectionHandler valueInjectionHandler = new ValueInjectionHandler(lastHandler, fwContext);
        SetterInjectionHandler setterInjectionHandler = new SetterInjectionHandler(valueInjectionHandler, fwContext);
        FieldInjectionHandler fieldInjectionHandler = new FieldInjectionHandler(setterInjectionHandler, fwContext);
        ConstructorInjectionHandler constructorInjectionHandler = new ConstructorInjectionHandler(fieldInjectionHandler, fwContext);
        return constructorInjectionHandler;
    }
}
