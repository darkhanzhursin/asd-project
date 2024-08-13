package application;

import framework.FWContext;

public class Application {
    public static void main(String[] args) {
        FWContext fwContext = new FWContext();
        fwContext.start(Application.class);
    }
}
