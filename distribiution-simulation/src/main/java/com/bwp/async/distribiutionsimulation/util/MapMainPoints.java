package com.bwp.async.distribiutionsimulation.util;

public enum MapMainPoints {

    MAP_HEIGHT(31),
    MAP_WIDTH(40),
    MAP_MID_POINT_Y(15),
    MAP_MID_POINT_X(30);

    private final int vaule;

    MapMainPoints(int vaule) {
        this.vaule = vaule;
    }

    public int getVaule() {
        return vaule;
    }
}
