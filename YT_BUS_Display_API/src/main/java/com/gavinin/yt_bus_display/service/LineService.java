package com.gavinin.yt_bus_display.service;

import com.gavinin.yt_bus_display.entity.Line;

import java.util.List;

/**
 * @ClassName LineService
 * @Description TODO
 * @Author gavin
 * @Date 5/7/2022 03:08
 * @Version 1.0
 **/
public interface LineService {
   List<Line> getLines();
}
