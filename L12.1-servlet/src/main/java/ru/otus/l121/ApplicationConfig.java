package ru.otus.l121;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Настрокий приложения
 */
public class ApplicationConfig {
    private static final String CONFIG_FILENAME = "config.properties";
    private static ApplicationConfig instance;

    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    private String webAdmin;
    private String webPassword;

    public static ApplicationConfig getInstance(){
        if(instance == null){
            instance = new ApplicationConfig();
        }

        return instance;
    }

    private ApplicationConfig(){
        Properties properties = getProperties();

        this.dbUrl = properties.getProperty("db_url");
        this.dbUser = properties.getProperty("db_user");
        this.dbPassword = properties.getProperty("db_password");

        this.webAdmin = properties.getProperty("web_admin");
        this.webPassword = properties.getProperty("web_password");
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

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getWebAdmin() {
        return webAdmin;
    }

    public String getWebPassword() {
        return webPassword;
    }
}
