package com.bwp.async.distribiutionsimulation.util;

public enum Direction {

    UP(-1, 0),
    STRAIGHT(0, 1),
    DOWN(1, 0);

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
