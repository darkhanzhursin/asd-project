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
        System.out.println(weatherService.getCurrentWeather());
        System.out.println(weatherService.getWeatherForecast());
        System.out.println(weatherService.convertCelsiusToFahrenheit(25.0));
    }
}
