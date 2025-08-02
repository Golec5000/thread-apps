package com.bwp.async.distribiutionsimulation.map;

import com.bwp.async.distribiutionsimulation.threads.Person;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.bwp.async.distribiutionsimulation.util.MapMainValues.*;

public class MainMap {

    private final List<List<GridElement>> grid;

    private static MainMap instance;

    private MainMap() {
        this.grid = mapInit();
    }

    public static MainMap getInstance() {
        MainMap result = instance;

        if (result != null) {
            return result;
        }

        synchronized (MainMap.class) {
            if (instance == null) {
                instance = new MainMap();
            }
            return instance;
        }
    }

    private List<List<GridElement>> mapInit() {
        List<List<GridElement>> grid = new ArrayList<>(MAP_HEIGHT);
        for (int y = 0; y < MAP_HEIGHT; y++) {
            List<GridElement> row = new ArrayList<>(MAP_WIDTH);
            for (int x = 0; x < MAP_WIDTH; x++) {
                row.add(new GridElement(x, y));
            }
            grid.add(row);
        }

        return grid;
    }

    private void renderLanes() {
        renderUpLane();
        renderMidLane();
        renderBottomLane();
        grid.get(MAP_MID_POINT_Y).get(MAP_MID_POINT_X).setColor(MAIN_POINTS_COLOR);
    }

    private void renderUpLane() {
        for (int i = MAP_MID_POINT_Y; i >= 0; i--)
            grid.get(i).get(MAP_MID_POINT_X).setColor(LINES_COLOR);
        for (int i = MAP_MID_POINT_X; i < MAP_WIDTH; i++)
            grid.get(0).get(i).setColor(LINES_COLOR);
        grid.get(0).get(MAP_WIDTH - 1).setColor(MAIN_POINTS_COLOR);
    }

    private void renderBottomLane() {
        for (int i = MAP_MID_POINT_Y; i < MAP_HEIGHT; i++)
            grid.get(i).get(MAP_MID_POINT_X).setColor(LINES_COLOR);
        for (int i = MAP_MID_POINT_X; i < MAP_WIDTH; i++)
            grid.get(MAP_HEIGHT - 1).get(i).setColor(LINES_COLOR);
        grid.get(MAP_HEIGHT - 1).get(MAP_WIDTH - 1).setColor(MAIN_POINTS_COLOR);
    }

    private void renderMidLane() {
        for (GridElement el : grid.get(MAP_MID_POINT_Y)) {
            el.setColor(LINES_COLOR);
        }
        grid.get(MAP_MID_POINT_Y).get(MAP_WIDTH - 1).setColor(MAIN_POINTS_COLOR);
    }

    public void renderMap(GraphicsContext gc, LinkedList<Person> clients) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        renderLanes();
        clients.stream()
                .filter(Person::isAlive)
                .forEach(client -> client.getGridElement().setColor(client.getColor()));
        grid.forEach(row -> row.forEach(el -> setElementInGrid(gc, el)));
    }

    private void setElementInGrid(GraphicsContext gc, GridElement el) {
        gc.setFill(el.getColor());
        gc.fillRect(el.getX(), el.getY(), GridElement.getDIM(), GridElement.getDIM());
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(0.5);
        gc.strokeRect(el.getX(), el.getY(), GridElement.getDIM(), GridElement.getDIM());
    }

    public List<List<GridElement>> getGrid() {
        return grid;
    }
}
