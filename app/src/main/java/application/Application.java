package application;

import framework.annotations.Autowired;
import framework.FWApplication;

import java.lang.reflect.InvocationTargetException;

public class Application implements Runnable {

    @Autowired
    private WeatherService weatherService;

    public static void main(String[] args) {
        FWApplication.run(Application.class);
    }

    @Override
    public void run() {
        try {
            weatherService.getCurrentWeather();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        weatherService.getWeatherForecast();
        weatherService.convertCelsiusToFahrenheit(25.0);
    }
}
