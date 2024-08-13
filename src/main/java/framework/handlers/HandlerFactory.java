package framework.handlers;

import framework.FWContext;

public class HandlerFactory {
    public static ServiceObjectHandler getChainHandler(FWContext fwContext){
        LastHandler lastHandler = new LastHandler(null, fwContext);
        ListenerHandler listenerHandler = new ListenerHandler(lastHandler, fwContext);
        ScheduledMethodsHandler scheduledMethodsHandler = new ScheduledMethodsHandler(listenerHandler, fwContext);
        ValueInjectionHandler valueInjectionHandler = new ValueInjectionHandler(scheduledMethodsHandler, fwContext);
        SetterInjectionHandler setterInjectionHandler = new SetterInjectionHandler(valueInjectionHandler, fwContext);
        FieldInjectionHandler fieldInjectionHandler = new FieldInjectionHandler(setterInjectionHandler, fwContext);
        ConstructorInjectionHandler constructorInjectionHandler = new ConstructorInjectionHandler(fieldInjectionHandler, fwContext);
        return constructorInjectionHandler;

    }
}
