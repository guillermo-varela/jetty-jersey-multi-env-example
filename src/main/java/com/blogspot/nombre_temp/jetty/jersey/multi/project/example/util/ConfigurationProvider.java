package com.blogspot.nombre_temp.jetty.jersey.multi.project.example.util;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

public class ConfigurationProvider {

    private ConfigurationProvider() {}

    private static PropertiesConfiguration serverConfiguration;
    private static PropertiesConfiguration applicationConfiguration;

    public static void startConfiguration() throws ConfigurationException {
        ClassLoader classLoader = ConfigurationProvider.class.getClassLoader();

        serverConfiguration = new PropertiesConfiguration();
        serverConfiguration.setFile(new File(classLoader.getResource("server.properties").getFile()));
        serverConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
        serverConfiguration.load();

        applicationConfiguration = new PropertiesConfiguration();
        applicationConfiguration.setFile(new File(classLoader.getResource("application.properties").getFile()));
        applicationConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
        applicationConfiguration.load();
    }

    public static PropertiesConfiguration getServerConfiguration() {
        return serverConfiguration;
    }

    public static PropertiesConfiguration getApplicationConfiguration() {
        return applicationConfiguration;
    }
}
