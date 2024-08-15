package application;

public interface WeatherService {
    void getCurrentWeather();
    void getWeatherForecast();
    void convertCelsiusToFahrenheit(double celsius);
    void convertFahrenheitToCelsius(double fahrenheit);
}
