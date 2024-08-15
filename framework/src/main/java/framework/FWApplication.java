package framework;

public class FWApplication {

    public static void run(Class applicationClass) {
        // create the context
        FWContext fwContext = new FWContext();
        fwContext.start(applicationClass);
    }
}
