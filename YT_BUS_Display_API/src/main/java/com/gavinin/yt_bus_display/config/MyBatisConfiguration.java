package com.gavinin.yt_bus_display.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MyBatisConfiguration
 * @Description TODO
 * @Author gavin
 * @Date 5/7/2022 15:39
 * @Version 1.0
 **/
@Configuration
@MapperScan(basePackages = {"com.gavinin.yt_bus_display.dao"})
public class MyBatisConfiguration {

}