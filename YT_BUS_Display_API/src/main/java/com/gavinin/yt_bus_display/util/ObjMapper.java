package com.gavinin.yt_bus_display.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @ClassName ObjMapper
 * @Description TODO
 * @Author gavin
 * @Date 5/7/2022 03:55
 * @Version 1.0
 **/
@Component
public class ObjMapper {

    @Bean
    public ObjectMapper objectMapper() {
      return new ObjectMapper();
    }
}
