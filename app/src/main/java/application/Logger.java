package application;

import framework.annotations.Service;

@Service
public class Logger {
    public void log(String msg) {
        System.out.println("Logger: " + msg);
    }
}
