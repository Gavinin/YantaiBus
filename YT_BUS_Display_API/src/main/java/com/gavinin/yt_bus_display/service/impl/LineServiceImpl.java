package com.gavinin.yt_bus_display.service.impl;

import com.gavinin.yt_bus_display.entity.OnlineBusDTO;
import com.gavinin.yt_bus_display.mapper.BusDBMapper;
import com.gavinin.yt_bus_display.entity.Line;
import com.gavinin.yt_bus_display.entity.Stop;
import com.gavinin.yt_bus_display.service.LineService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public List<Line> getLines(OnlineBusDTO onlineBusDTO) {
        if (onlineBusDTO.getLineName().isBlank()) {
            onlineBusDTO.setLineName("");
        }else {
            onlineBusDTO.setLineName("%" + onlineBusDTO.getLineName() + "%");
        }

        return busDBMapper.findByLines(onlineBusDTO.getLineName(), onlineBusDTO.getDirection());


    }

    @Override
    public List<Stop> getStopsByLine(String lineName, String directionEnum) {
        return busDBMapper.selectStopByLine(lineName, directionEnum);
    }


}
