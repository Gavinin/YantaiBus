package com.gavinin.yt_bus_display.controller;

import com.gavinin.yt_bus_display.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author gavin
 * @Date 5/7/2022 15:51
 * @Version 1.0
 **/

@RestController
public class HelloController {
    final
    LineService lineService;

    public HelloController(LineService lineService) {
        this.lineService = lineService;
    }

    @GetMapping("/allbus")
    public Object listAllBus() {
        return lineService.getLines();
    }
}
