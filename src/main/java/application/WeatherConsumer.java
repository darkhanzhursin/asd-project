package application;

import framework.Autowired;
import framework.Service;

@Service
public class WeatherConsumer {
    private final String city = "Fairfield";

    @Autowired
    private WeatherService weatherService;

    public void consumeWeather() {
        System.out.println(weatherService.getCurrentWeather(city));
        System.out.println(weatherService.getWeatherForecast(city));
        System.out.println(weatherService.convertCelsiusToFahrenheit(25.0));
    }
}
