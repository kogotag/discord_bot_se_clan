package ru.kogotag.discord;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;

public class Config {
    private static Config config;
    private String token;
    private String prefix;
    private String currency;
    private String owner;
    public static Config getConfig() {
        if (config == null) {
            try {
                String pathToConfig = new File(Config.class.getProtectionDomain().getCodeSource().getLocation().getPath())
                        .getParent() + "/config.yaml";
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                config = mapper.readValue(new File(pathToConfig), Config.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return config;
    }

    public Config() {
    }

    public String getToken() {
        return token;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getCurrency() {
        return currency;
    }

    public String getOwner() {
        return owner;
    }
}
