package application;

import framework.annotations.Autowired;
import framework.annotations.Qualifier;
import framework.annotations.Service;
import framework.annotations.Value;

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
    public String getCurrentWeather() {
        logger.log("weather for " + theCity);
        return "The current weather in " + theCity + " is sunny with a temperature of 25°C.";
    }

    @Override
    public String getWeatherForecast() {
        return "The weather forecast for " + theCity + " is sunny for the next 3 days.";
    }

    @Override
    public double convertCelsiusToFahrenheit(double celsius) {
        return (celsius * 9/5) + 32;
    }

    @Override
    public double convertFahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5/9;
    }
}
