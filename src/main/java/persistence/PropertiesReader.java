package persistence;

import java.io.InputStream;
import java.util.Properties;

public enum PropertiesReader {
    Instance;
    Properties properties;
    String HOST;
    String USER;
    String PASS;
    String DRIVER;
    PropertiesReader() {
        properties = new Properties();
        InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.dev.properties");
        try {
            properties.load(file);
            HOST = properties.getProperty("DB.HOST");
            USER = properties.getProperty("DB.USER");
            PASS = properties.getProperty("DB.PASS");
            DRIVER = properties.getProperty("DB.ClassDriver");
        } catch (Exception e) {e.printStackTrace();}
    }



}
