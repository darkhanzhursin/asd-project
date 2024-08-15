package application.temperature;

import framework.annotations.Profile;
import framework.annotations.Service;

@Service
@Profile(value = "fahrenheit")
public class FahrenheitMeasureTemp implements IMeasureTemp {
    @Override
    public void getTemperature(double temp) {
        double res = (temp * 9/5) + 32; // converting from Celsius to Fahrenheit
        System.out.println("Temperature is: " + res + " Fahrenheit.");
    }
}
