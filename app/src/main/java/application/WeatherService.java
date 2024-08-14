package application;

public interface WeatherService {
    String getCurrentWeather();
    String getWeatherForecast();
    double convertCelsiusToFahrenheit(double celsius);
    double convertFahrenheitToCelsius(double fahrenheit);
}
