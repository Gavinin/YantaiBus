package com.gavinin.yt_bus_display.common;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @ClassName ConstStatus
 * @Description TODO
 * @Author gavin
 * @Date 5/7/2022 03:43
 * @Version 1.0
 **/
@Component
public class ConstStatus {

    public static String DB_LOCAL;

    public static String SYS_ROOT;

    public static final String DB_NAME = "/ytcx.db";

    @PostConstruct
    void init() {
        String root = System.getProperty("user.dir");
        SYS_ROOT = root;
        DB_LOCAL = root + "/DB";
    }
}
