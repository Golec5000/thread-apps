package com.bwp.async.distribiutionsimulation.map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridElement extends Rectangle {
    private static final double DIM = 15.0;
    private boolean isOccupied;

    public GridElement(double x, double y, Color color) {
        super(DIM, DIM); // ustawia szerokość i wysokość
        this.isOccupied = false;

        setX(x);
        setY(y);
        setFill(color);              // kolor wypełnienia
        setStroke(Color.GRAY);       // kolor obramowania
        setStrokeWidth(0.5);         // grubość obramowania
    }

    public static double getDIM() {
        return DIM;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

}
