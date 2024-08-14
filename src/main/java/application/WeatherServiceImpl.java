package application;

import framework.annotation.Service;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Override
    public String getCurrentWeather(String cityName) {
        return "The current weather in " + cityName + " is sunny with a temperature of 25°C.";
    }

    @Override
    public String getWeatherForecast(String cityName) {
        return "The weather forecast for " + cityName + " is sunny for the next 3 days.";
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
