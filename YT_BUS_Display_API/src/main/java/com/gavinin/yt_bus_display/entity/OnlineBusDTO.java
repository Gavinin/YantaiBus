package com.gavinin.yt_bus_display.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnlineBusDTO {
    @JsonProperty("linename")
    private String lineName;
    @JsonProperty("upordown")
    private String direction;
}
