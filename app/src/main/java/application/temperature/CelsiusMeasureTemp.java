package application.temperature;

import framework.annotations.Profile;
import framework.annotations.Service;

@Service
@Profile(value = "celsius")
public class CelsiusMeasureTemp implements IMeasureTemp {

    @Override
    public void getTemperature(double temp) {
        double res = (temp - 32) * 5/9; // converting from Fahrenheit to Celsius
        System.out.println("Temperature is " + res + " Celsius.");
    }
}
