package com.gavinin.yt_bus_display.service.impl;

import com.gavinin.yt_bus_display.dao.BusDBMapper;
import com.gavinin.yt_bus_display.entity.Line;
import com.gavinin.yt_bus_display.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName LineServiceImpl
 * @Description TODO
 * @Author gavin
 * @Date 5/7/2022 03:11
 * @Version 1.0
 **/
@Service
public class LineServiceImpl implements LineService {

    @Resource
    BusDBMapper busDBMapper;

//    private final DriverManagerDataSource dataSource;
//
//    public LineServiceImpl(DriverManagerDataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    @Override
    public List<Line> getLines() {
        return busDBMapper.findAllLines();

    }
}
