package ru.otus.l91;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Настрокий подключения к БД
 */
public class DbConfig {
    private static final String CONFIG_FILENAME = "config.properties";
    private static DbConfig instance;

    private String url;
    private String user;
    private String password;

    public static DbConfig getInstance(){
        if(instance == null){
            instance = new DbConfig();
        }

        return instance;
    }

    private DbConfig(){
        Properties properties = getProperties();

        this.url = properties.getProperty("url");
        this.user = properties.getProperty("user");
        this.password = properties.getProperty("password");
    }

    private Properties getProperties() {
        Properties prop = new Properties();

        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILENAME)) {
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new RuntimeException("Property file '" + CONFIG_FILENAME + "' not found in the classpath");
            }
        }catch (IOException ex){
            throw new RuntimeException("Error on reading '" + CONFIG_FILENAME + "'",ex);
        }

        return prop;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
