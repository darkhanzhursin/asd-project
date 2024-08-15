package application;

import framework.annotations.*;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Value(name = "city")
    String theCity;

    //@Autowired
    @Qualifier(name = "application.Logger")
    private Logger logger;

    public WeatherServiceImpl() {
    }

    @Autowired
    public WeatherServiceImpl(Logger logger) {
        this.logger = logger;
    }

    @Override
    @Scheduled(cron = "10 0")
    public void getCurrentWeather() {
        logger.log("weather for " + theCity);
        System.out.println("The current weather in " + theCity + " is sunny with a temperature of 25°C.");
    }


    @Override
    @Scheduled(fixedRate = 5000)
    public void getWeatherForecast() {
        System.out.println("The weather forecast for " + theCity + " is sunny for the next 3 days.");
    }

    @Override
    public void convertCelsiusToFahrenheit(double celsius) {
        double res = (celsius * 9/5) + 32;
        System.out.println(res);
    }

    @Override
    public void convertFahrenheitToCelsius(double fahrenheit) {
        double res = (fahrenheit - 32) * 5/9;
        System.out.println(res);
    }
}
