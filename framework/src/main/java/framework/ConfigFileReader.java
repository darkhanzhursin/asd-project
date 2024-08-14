package framework;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigFileReader {

    public static Properties readConfigFile() {
        Properties prop = null;

        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        try {
            prop = new Properties();
            prop.load(new FileInputStream(rootPath + "/application.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop;
    }
}
