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
        String city = "Fairfield";
        System.out.println(weatherService.getCurrentWeather(city));
        System.out.println(weatherService.getWeatherForecast(city));
        System.out.println(weatherService.convertCelsiusToFahrenheit(25.0));
    }
}
