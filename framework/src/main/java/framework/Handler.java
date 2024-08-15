package framework;

import framework.handlers.*;

public class Handler {

    public static ServiceObjectHandler getChainHandler(FWContext fwContext) {
        LastHandler lastHandler = new LastHandler(null, fwContext);
        ConfigurationPropertiesHandler configurationPropertiesHandler =
                new ConfigurationPropertiesHandler(lastHandler, fwContext);
        EventHandler eventHandler = new EventHandler(configurationPropertiesHandler, fwContext);
        ScheduledMethodsHandler scheduledMethodsHandler = new ScheduledMethodsHandler(eventHandler, fwContext);
        ValueInjectionHandler valueInjectionHandler = new ValueInjectionHandler(scheduledMethodsHandler, fwContext);
        SetterInjectionHandler setterInjectionHandler = new SetterInjectionHandler(valueInjectionHandler, fwContext);
        FieldInjectionHandler fieldInjectionHandler = new FieldInjectionHandler(setterInjectionHandler, fwContext);
        ConstructorInjectionHandler constructorInjectionHandler = new ConstructorInjectionHandler(fieldInjectionHandler, fwContext);
        return constructorInjectionHandler;
    }
}
