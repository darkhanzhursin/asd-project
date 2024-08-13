package application;

public interface WeatherService {
    String getCurrentWeather(String cityName);
    String getWeatherForecast(String cityName);
    double convertCelsiusToFahrenheit(double celsius);
    double convertFahrenheitToCelsius(double fahrenheit);
}
