package com.bwp.async.distribiutionsimulation.threads;

import com.bwp.async.distribiutionsimulation.map.GridElement;
import com.bwp.async.distribiutionsimulation.map.MainMap;
import com.bwp.async.distribiutionsimulation.util.Direction;
import javafx.scene.paint.Color;

import java.security.SecureRandom;

import static com.bwp.async.distribiutionsimulation.util.MapMainPoints.MAP_WIDTH;

public class Person extends Thread {

    private final int speed;
    private final MainMap map = MainMap.getInstance();
    private final Color color;

    private GridElement gridElement;
    private Direction direction;
    private boolean isRunning;
    private boolean toErase;

    public Person(GridElement element){
        gridElement = element;
        speed = new SecureRandom().nextInt(1000, 2000);
        color = Color.BLUE;
        isRunning = true;
        toErase = false;
        direction = Direction.STRAIGHT;
    }

    @Override
    public void run() {
        while (isRunning){
            move();
        }
    }

    private void move() {
        int nextX = gridElement.getC_x() + direction.getX();
        int nextY = gridElement.getC_y() + direction.getY();

        System.out.println("X: " + gridElement.getX() + " Y: " + gridElement.getY());

        GridElement nextElement = map.getGrid().get(nextX).get(nextY);
        nextElement.canMove(this);

        lastMove();
    }

    private void lastMove() {
        if (gridElement.getC_x() < MAP_WIDTH.getVaule() - 1) return;
        isRunning = false;
        toErase = true;
    }

    public Color getColor() {
        return color;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setGridElement(GridElement gridElement) {
        this.gridElement = gridElement;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
