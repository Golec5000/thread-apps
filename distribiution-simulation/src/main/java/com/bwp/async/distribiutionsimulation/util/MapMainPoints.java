package com.bwp.async.distribiutionsimulation.util;

public enum MapMainPoints {

    MAP_HEIGHT(31),         // Y
    MAP_WIDTH(40),          // X
    MAP_MID_POINT_Y(15),    // flor(Y/2)
    MAP_MID_POINT_X(30);

    private final int value;

    MapMainPoints(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
