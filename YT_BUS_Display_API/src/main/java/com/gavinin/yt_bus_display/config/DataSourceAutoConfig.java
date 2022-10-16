//package com.gavinin.yt_bus_display.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//
//import static com.gavinin.yt_bus_display.common.ConstStatus.*;
//
///**
// * @ClassName DataSourceAutoConfig
// * @Description TODO
// * @Author gavin
// * @Date 5/7/2022 14:39
// * @Version 1.0
// **/
//@Configuration
//public class DataSourceAutoConfig {
//    public static final String JDBC_Prefix = "jdbc:sqlite:";
//
//
//    @Bean("dataSource")
//    public DriverManagerDataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.sqlite.JDBC");
//        dataSource.setUrl(JDBC_Prefix + DB_LOCAL + DB_NAME);
//        dataSource.setUsername("");
//        dataSource.setPassword("");
//        return dataSource;
//    }
//
//}
