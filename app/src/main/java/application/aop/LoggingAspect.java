package application.aop;

import framework.annotations.After;
import framework.annotations.Before;

public class LoggingAspect {
    @Before(pointcut = "application.aop.Customer.setName")
    public static void beforeSetName() {
        System.out.println("Before setting name ");
    }

    @After(pointcut = "application.aop.Customer.setName")
    public static void afterSetName() {
        System.out.println("After setting name: ");
    }
}
