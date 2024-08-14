package framework.handlers;

import framework.FWContext;

public class LastHandler extends ServiceObjectHandler {

    public LastHandler(ServiceObjectHandler nextHandler, FWContext fwContext) {
        super(nextHandler, fwContext);
    }

    @Override
    public void handle(Object serviceObject) {

    }
}
