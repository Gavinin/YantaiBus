package com.gavinin.yt_bus_display.mapper;

import com.gavinin.yt_bus_display.entity.Line;
import com.gavinin.yt_bus_display.entity.Stop;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public interface BusDBMapper {

    @Select("select linename as line, startsite||'-'||endsite as detail  from ytcx_line group by id ")
    List<Line> findAllLines() ;


    List<Stop> selectStopByLine(String lineName, String directionEnum);

    List<Line> findByLines(String line,String direction);
}
