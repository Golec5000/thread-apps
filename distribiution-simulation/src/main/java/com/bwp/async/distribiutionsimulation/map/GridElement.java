package com.bwp.async.distribiutionsimulation.map;

import com.bwp.async.distribiutionsimulation.threads.Person;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridElement extends Rectangle {
    private static final double DIM = 15.0;

    private final int cordX;
    private final int cordY;

    private boolean isOccupied;

    public GridElement(double x, double y, Color color, int cX, int cY) {
        super(DIM, DIM); // ustawia szerokość i wysokość
        cordX = cX;
        cordY = cY;
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

    public synchronized boolean canMove(Person person) {
        person.setGridElement(this);
        Platform.runLater(() -> setFill(person.getColor()));
        return true;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public int getCordX() {
        return cordX;
    }

    public int getCordY() {
        return cordY;
    }
}
