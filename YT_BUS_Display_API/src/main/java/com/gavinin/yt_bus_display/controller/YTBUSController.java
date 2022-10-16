package com.gavinin.yt_bus_display.controller;

import com.gavinin.yt_bus_display.common.DirectionEnum;
import com.gavinin.yt_bus_display.entity.OnlineBusDTO;
import com.gavinin.yt_bus_display.entity.Result;
import com.gavinin.yt_bus_display.entity.Stop;
import com.gavinin.yt_bus_display.service.LineService;
import com.gavinin.yt_bus_display.task.UpdateMapTask;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author gavin
 * @Date 5/7/2022 15:51
 * @Version 1.0
 **/

@RestController
public class YTBUSController {
    final
    LineService lineService;

    public YTBUSController(LineService lineService) {
        this.lineService = lineService;
    }

    @GetMapping("/ytbus/getLines")
    public Object listAllBus(@RequestParam("line") String line, @RequestParam("direction") String direction) {
        String directionEnum = DirectionEnum.UPWARD.toString();
        if (DirectionEnum.DOWNWARD.toString().equals(direction)) {
            directionEnum = DirectionEnum.DOWNWARD.toString();
        }
        OnlineBusDTO onlineBusDTO = new OnlineBusDTO(line, directionEnum);

        return Result.success(lineService.getLines(onlineBusDTO));
    }

    @PostMapping("/ytbus/getMap")
    public Object getStopsByLineName(@RequestBody OnlineBusDTO onlineBusDTO) {
        String directionEnum = DirectionEnum.UPWARD.toString();
        if (DirectionEnum.DOWNWARD.toString().equals(onlineBusDTO.getDirection())) {
            directionEnum = DirectionEnum.DOWNWARD.toString();
        }
        List<Stop> stopsByLine = lineService.getStopsByLine(onlineBusDTO.getLineName(), directionEnum);
        for (Stop stop : stopsByLine) {
            String stopName = stop.getStopName();
            if (stopName.contains("[=") && stopName.contains("]")) {
                stopName = stopName.substring(0, stopName.indexOf("[=")) + stopName.substring(stopName.indexOf("]")+1);
                stop.setStopName(stopName);
            }
        }
        return Result.success(stopsByLine);
    }

//测试用
//    @Resource
//    UpdateMapTask updateMapTask;

//    @GetMapping("/ytbus/up")
//    public Object updateDB() throws IOException {
//        updateMapTask.check();
//        return Result.success("OK");
//    }
}
