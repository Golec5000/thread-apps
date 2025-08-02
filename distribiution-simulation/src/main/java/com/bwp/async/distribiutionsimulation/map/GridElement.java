package com.bwp.async.distribiutionsimulation.map;

import javafx.scene.paint.Color;

import static com.bwp.async.distribiutionsimulation.util.MapMainValues.DEFAULT_COLOR;
import static com.bwp.async.distribiutionsimulation.util.MapMainValues.LINES_COLOR;

public class GridElement {
    private static final double DIM = 15.0;

    private final int cordX;
    private final int cordY;

    private Color color = DEFAULT_COLOR;
    private boolean isOccupied = false;

    public GridElement(int x, int y) {
        this.cordX = x;
        this.cordY = y;
    }

    public double getX() {
        return cordX * DIM;
    }

    public double getY() {
        return cordY * DIM;
    }

    public static double getDIM() {
        return DIM;
    }

    public int getCordX() {
        return cordX;
    }

    public int getCordY() {
        return cordY;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void resetColor(){
        this.color = LINES_COLOR;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
}
