package com.bwp.async.distribiutionsimulation.threads;

import com.bwp.async.distribiutionsimulation.map.GridElement;
import com.bwp.async.distribiutionsimulation.map.MainMap;
import com.bwp.async.distribiutionsimulation.util.Direction;
import javafx.scene.paint.Color;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.bwp.async.distribiutionsimulation.util.Direction.STRAIGHT;
import static com.bwp.async.distribiutionsimulation.util.MapMainValues.*;

public class Person extends Thread {

    private final int speed;
    private final MainMap map = MainMap.getInstance();
    private final Color color;
    private final AtomicBoolean isRunning = new AtomicBoolean(true);

    private boolean hasCrossedSwitch;
    private GridElement gridElement;
    private Direction direction;

    public Person(int speed) {
        this.speed = speed;
        this.color = Color.BLUE;
        this.direction = STRAIGHT;
        setDaemon(true);
        this.gridElement = map.getGrid().get(MAP_MID_POINT_Y).get(0);
        this.hasCrossedSwitch = false;
    }

    @Override
    public void run() {
        while (isRunning.get() && !isInterrupted()) {
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

        if (!isRunning.get()) return;

        if (gridElement.getCordY() == 0 || gridElement.getCordY() == MAP_HEIGHT - 1) direction = STRAIGHT;

        int nextX = gridElement.getCordX() + direction.getX();
        int nextY = gridElement.getCordY() + direction.getY();

        setGridElement(map.getGrid().get(nextY).get(nextX));

        setClientDirection();

        lastMove();
    }

    private void setClientDirection() {
        if (hasCrossedSwitch) return;

        if (gridElement.getCordY() == MAP_MID_POINT_Y && gridElement.getCordX() == MAP_MID_POINT_X) {
            this.direction = MAP_SWITCH_DIRECTION.get();
            this.hasCrossedSwitch = true;
        }
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

    @Override
    public void interrupt() {
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
