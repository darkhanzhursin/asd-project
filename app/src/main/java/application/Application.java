package application;

import application.aop.Customer;
import application.aop.CustomerInterface;
import application.aop.LoggingAspect;
import framework.AOPProxy;
import framework.annotations.Autowired;
import framework.FWApplication;
import framework.handlers.AOPHandler;

import java.lang.reflect.InvocationTargetException;

public class Application implements Runnable {

    @Autowired
    private WeatherService weatherService;

    public static void main(String[] args) {
        FWApplication.run(Application.class);
        CustomerInterface customer = new Customer();
        AOPHandler handler = new AOPHandler(new LoggingAspect());
        CustomerInterface proxy = AOPProxy.createProxy(customer, handler);

        proxy.setName("John Doe");
    }

    @Override
    public void run() {
        try {
            weatherService.getCurrentWeather();
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        weatherService.getWeatherForecast();
        weatherService.convert();
    }
}
