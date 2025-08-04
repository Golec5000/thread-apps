package com.bwp.async.distribiutionsimulation.map;

import com.bwp.async.distribiutionsimulation.threads.Person;
import javafx.scene.paint.Color;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.bwp.async.distribiutionsimulation.util.MapMainValues.DEFAULT_COLOR;

public class GridElement {
    private static final double DIM = 15.0;

    private final Lock lock = new ReentrantLock();
    private final Condition caMoveCondition = lock.newCondition();

    private boolean isOccupied = false;
    private final int cordX;
    private final int cordY;

    private Color color = DEFAULT_COLOR;

    public GridElement(int x, int y) {
        this.cordX = x;
        this.cordY = y;
    }

    public void acquireElement(Person person) throws InterruptedException {
        lock.lock();
        try {
            while (this.isOccupied && person.isAlive()) caMoveCondition.await();
            this.isOccupied = true;
        } finally {
            lock.unlock();
        }
    }

    public void notifyElement() {
        lock.lock();
        try {
            this.isOccupied = false;
            caMoveCondition.signal();
        } finally {
            lock.unlock();
        }
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

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        this.isOccupied = occupied;
    }
}
