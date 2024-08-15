package application;

import application.event.TempChangeEvent;
import application.notification.ISender;
import application.notification.SMSSender;
import application.temperature.IMeasureTemp;
import framework.annotations.*;
import framework.events.EventPublisher;

import java.lang.reflect.InvocationTargetException;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Value(name = "city")
    String theCity;

    @Autowired
    private SMSSender smsSender;

    @Qualifier(name = "application.Logger")
    private Logger logger;

    public WeatherServiceImpl() {
    }

    private ISender emailSender;

    @Autowired
    public WeatherServiceImpl(Logger logger) {
        this.logger = logger;
    }

    @Autowired
    public void setEmailSender(ISender emailSender) {
        this.emailSender = emailSender;
    }

    @Autowired
    EventPublisher publisher;

    @Autowired
    private IMeasureTemp measureTemp;

    @Override
    @Scheduled(cron = "10 0")
    public void getCurrentWeather() throws InvocationTargetException, IllegalAccessException {
        logger.log("weather for " + theCity);
        System.out.println("The current weather in " + theCity + " is sunny with a temperature of 25°C.");
        publisher.publish(new TempChangeEvent());
        emailSender.sendEmail("weather for " + theCity);
    }

    @Override
    @Scheduled(fixedRate = 5000)
    public void getWeatherForecast() {
        System.out.println("The weather forecast for " + theCity + " is sunny for the next 3 days.");
        smsSender.sendSMS();
    }

//    @Override
//    public void convertCelsiusToFahrenheit(double celsius) {
//        double res = (celsius * 9/5) + 32;
//        System.out.println(res);
//    }
//
//    @Override
//    public void convertFahrenheitToCelsius(double fahrenheit) {
//        double res = (fahrenheit - 32) * 5/9;
//        System.out.println(res);
//    }


    @Override
    public void convert() {
        measureTemp.getTemperature(40);
    }
}
