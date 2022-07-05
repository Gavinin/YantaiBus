package com.gavinin.yt_bus_display.dao;

import com.gavinin.yt_bus_display.entity.Line;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public interface BusDBMapper {

    @Select("select distinct linename as line, startsite||'-'||endsite as detail  from ytcx_line ")
    List<Line> findAllLines() ;
}
