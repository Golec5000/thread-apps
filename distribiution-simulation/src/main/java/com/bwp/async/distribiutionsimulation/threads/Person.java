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
        speed = new SecureRandom().nextInt(100, 700);
        color = Color.BLUE;
        isRunning = true;
        toErase = false;
        direction = Direction.STRAIGHT;
    }

    @Override
    public void run() {
        while (isRunning){
            move();
            try {
                sleep(speed);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

    private void move() {
        int nextX = gridElement.getCordX() + direction.getX();
        int nextY = gridElement.getCordY() + direction.getY();

        System.out.println("X: " + gridElement.getCordX() + " Y: " + gridElement.getCordY());

        GridElement nextElement = map.getGrid().get(nextY).get(nextX);
        nextElement.canMove(this);

        lastMove();
    }

    private void lastMove() {
        if (gridElement.getCordX() < MAP_WIDTH.getValue() - 1) return;
        isRunning = false;
//        toErase = true;
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
