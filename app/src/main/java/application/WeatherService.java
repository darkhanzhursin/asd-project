package application;

import java.lang.reflect.InvocationTargetException;

public interface WeatherService {
    void getCurrentWeather() throws InvocationTargetException, IllegalAccessException;
    void getWeatherForecast();
    void convertCelsiusToFahrenheit(double celsius);
    void convertFahrenheitToCelsius(double fahrenheit);
}
