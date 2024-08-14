package application;

import framework.annotation.Autowired;
import framework.annotation.Component;
import framework.annotation.Service;

@Component
public class WeatherConsumer {
    private final String city = "Fairfield";

//    @Autowired
    private WeatherService weatherService;

//    @Autowired
//    public WeatherConsumer(WeatherService weatherService) {
//        this.weatherService = weatherService;
//    }


    @Autowired
    public void setWeatherService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public void consumeWeather() {
        System.out.println(weatherService.getCurrentWeather(city));
        System.out.println(weatherService.getWeatherForecast(city));
        System.out.println(weatherService.convertCelsiusToFahrenheit(25.0));
    }
}
