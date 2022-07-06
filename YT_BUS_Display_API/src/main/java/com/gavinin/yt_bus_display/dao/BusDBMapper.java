package com.gavinin.yt_bus_display.dao;

import com.gavinin.yt_bus_display.entity.Line;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public interface BusDBMapper {

    @Select("select linename as line, startsite||'-'||endsite as detail  from ytcx_line group by line")
    List<Line> findAllLines() ;
}
