package com.bwp.async.distribiutionsimulation.util;

import javafx.scene.paint.Color;

public enum Direction {

    UP(0, -1, Color.rgb(255, 155, 0)),
    STRAIGHT(1, 0, Color.rgb(41, 255, 4)),
    DOWN(0, 1, Color.rgb(212, 48, 172));

    private final int x;
    private final int y;
    private final Color color;

    Direction(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }
}
