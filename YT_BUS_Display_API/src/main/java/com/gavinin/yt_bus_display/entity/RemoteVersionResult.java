package com.gavinin.yt_bus_display.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Result
 * @Description TODO
 * @Author gavin
 * @Date 5/7/2022 02:56
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoteVersionResult {

    private UpdateResource data;
    private String info;
    private Integer status;

}
