package com.bwp.async.distribiutionsimulation.util;

public enum Direction {

    UP(0, -1),
    STRAIGHT(1, 0),
    DOWN(0, 1);

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
