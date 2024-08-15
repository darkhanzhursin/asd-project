package application;

import framework.annotations.Autowired;
import framework.FWApplication;

public class Application implements Runnable {

    @Autowired
    private WeatherService weatherService;

    public static void main(String[] args) {
        FWApplication.run(Application.class);
    }

    @Override
    public void run() {
        weatherService.getCurrentWeather();
        weatherService.getWeatherForecast();
        weatherService.convertCelsiusToFahrenheit(25.0);
    }
}
