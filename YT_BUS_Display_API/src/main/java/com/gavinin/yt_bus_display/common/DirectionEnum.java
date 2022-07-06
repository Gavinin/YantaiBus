package com.gavinin.yt_bus_display.common;

public enum DirectionEnum {
    UPWARD("上行"),
    DOWNWARD("下行");

    final String direction;

    DirectionEnum(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return direction;
    }
}
