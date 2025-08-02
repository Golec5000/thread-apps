package com.bwp.async.distribiutionsimulation.threads;

import com.bwp.async.distribiutionsimulation.map.GridElement;
import com.bwp.async.distribiutionsimulation.map.MainMap;
import com.bwp.async.distribiutionsimulation.util.Direction;
import javafx.scene.paint.Color;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.bwp.async.distribiutionsimulation.util.Direction.*;
import static com.bwp.async.distribiutionsimulation.util.MapMainValues.*;

public class Person extends Thread {

    private final int speed;
    private final MainMap map = MainMap.getInstance();
    private final Color color;

    private GridElement gridElement;
    private Direction direction;
    private AtomicBoolean isRunning;

    public Person(GridElement element){
        gridElement = element;
        speed = new SecureRandom().nextInt(100, 700);
        color = Color.BLUE;
        isRunning = new AtomicBoolean(true);
        direction = STRAIGHT;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (isRunning.get() && !isInterrupted()){
            move();
            try {
                sleep(speed);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println(this.getName() + " przerwany");
            }
        }
    }

    private void move() {

        if(!isRunning.get()) return;

        if(gridElement.getCordX() == 0 || gridElement.getCordX() == MAP_HEIGHT - 1) direction = STRAIGHT;

        int nextX = gridElement.getCordX() + direction.getX();
        int nextY = gridElement.getCordY() + direction.getY();

        System.out.println("X: " + gridElement.getCordX() + " Y: " + gridElement.getCordY());

        GridElement nextElement = map.getGrid().get(nextY).get(nextX);
        this.setGridElement(nextElement);

        lastMove();
    }

    private void lastMove() {
        if (gridElement.getCordX() < MAP_WIDTH - 1) return;
        isRunning.set(false);
    }

    public Color getColor() {
        return color;
    }

    public GridElement getGridElement() {
        return gridElement;
    }

    public void setGridElement(GridElement gridElement) {
        this.gridElement = gridElement;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void interrupt(){
        try {
            isRunning.set(false);
            this.join();
            System.out.println("Person " + this.getName() + " finish");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Błąd przy joinowaniu Person.");
        }
    }
}
