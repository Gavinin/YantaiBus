package com.gavinin.yt_bus_display.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName RestConfig
 * @Description TODO
 * @Author gavin
 * @Date 5/7/2022 03:05
 * @Version 1.0
 **/
@Configuration
public class RestConfig {

    final
    RestTemplateBuilder restTemplateBuilder;

    public RestConfig(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder.build();
    }

}
