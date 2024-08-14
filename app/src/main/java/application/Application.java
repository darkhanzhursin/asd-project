package application;

import framework.Autowired;
import framework.FWApplication;

public class Application implements Runnable {

    @Autowired
    private WeatherService weatherService;

    private final String city = "Fairfield";

    public static void main(String[] args) {
        FWApplication.run(Application.class);
    }

    @Override
    public void run() {
        System.out.println(weatherService.getCurrentWeather(city));
        System.out.println(weatherService.getWeatherForecast(city));
        System.out.println(weatherService.convertCelsiusToFahrenheit(25.0));
    }
}
