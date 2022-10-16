package com.gavinin.yt_bus_display.task;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Properties;

/**
 * @ClassName BusUtil
 * @Description TODO
 * @Author gavin
 * @Date 5/7/2022 14:31
 * @Version 1.0
 **/
@Component
public class BusVersionUtil {

    public static final String CONFIG_FILE_NAME = "BusConfig";
    public static final String CURRENT_VERSION_FLAG = "CURRENT_VERSION";

    public static String ORIGIN_VERSION = "20201203";

    public static String CURRENT_VERSION = "";

    public static String getVersionFromFileName(String fileName) {
        String version = fileName.substring(4, fileName.indexOf(".zip"));
        if (version.isBlank()) {
            return ORIGIN_VERSION;
        }
        return version;
    }

    public static String getValueFromProperties(String key) {
        String result = null;
        Properties properties = new Properties();
        try {
            properties.load(new BufferedInputStream(new FileInputStream(CONFIG_FILE_NAME)));
        } catch (IOException ignore) {
            properties.put(CURRENT_VERSION_FLAG, ORIGIN_VERSION);
            initConfigProperties(properties);

        }

        String value = properties.getProperty(key);
        if (!value.isBlank()) {
            result = value;
        }
        return result;
    }

    @PostConstruct
    public void init() {
        String valueFromProperties = getValueFromProperties(CURRENT_VERSION_FLAG);
        if (valueFromProperties.isBlank()) {
            Properties properties = new Properties();
            properties.put(CURRENT_VERSION_FLAG, ORIGIN_VERSION);
            initConfigProperties(properties);
        } else {
            CURRENT_VERSION = valueFromProperties;
        }

    }

    private static void initConfigProperties(Properties pro) {
        try {
            pro.store(new BufferedOutputStream(new FileOutputStream(CONFIG_FILE_NAME)), "Save Configs File.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
