package com.bwp.async.distribiutionsimulation.map;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static com.bwp.async.distribiutionsimulation.util.MapMainPoints.*;

public class MainMap {

    private List<List<GridElement>> grid;

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
        List<List<GridElement>> grid = new ArrayList<>(MAP_HEIGHT.getValue());
        for (int y = 0; y < MAP_HEIGHT.getValue(); y++) {
            List<GridElement> row = new ArrayList<>(MAP_WIDTH.getValue());
            for (int x = 0; x < MAP_WIDTH.getValue(); x++) {
                row.add(new GridElement(x * GridElement.getDIM(), y * GridElement.getDIM(), Color.CORNSILK, x, y));
            }
            grid.add(row);
        }
        return grid;
    }

    public void renderMap(AnchorPane drawingPane) {
        //Basic map part
        renderUpLane();
        renderMidLane();
        renderBottomLane();
        grid.get(MAP_MID_POINT_Y.getValue())
                .get(MAP_MID_POINT_X.getValue())
                .setFill(Color.rgb(200, 200, 200));

        drawingPane.getChildren().clear();
        grid.forEach(row -> drawingPane.getChildren().addAll(row));
    }

    private void renderUpLane() {
        for (int i = MAP_MID_POINT_Y.getValue(); i >= 0; i--)
            grid.get(i).get(MAP_MID_POINT_X.getValue()).setFill(Color.RED);
        for (int i = MAP_MID_POINT_X.getValue(); i < MAP_WIDTH.getValue(); i++)
            grid.get(0).get(i).setFill(Color.RED);
        grid.get(0).get(MAP_WIDTH.getValue() - 1).setFill(Color.rgb(200, 200, 200));
    }

    private void renderBottomLane() {
        for (int i = MAP_MID_POINT_Y.getValue(); i < MAP_HEIGHT.getValue(); i++)
            grid.get(i).get(MAP_MID_POINT_X.getValue()).setFill(Color.RED);
        for (int i = MAP_MID_POINT_X.getValue(); i < MAP_WIDTH.getValue(); i++)
            grid.get(MAP_HEIGHT.getValue() - 1).get(i).setFill(Color.RED);
        grid.get(MAP_HEIGHT.getValue() - 1).get(MAP_WIDTH.getValue() - 1).setFill(Color.rgb(200, 200, 200));
    }

    private void renderMidLane() {
        grid.get(MAP_MID_POINT_Y.getValue()).forEach(element -> element.setFill(Color.RED));
        grid.get(MAP_MID_POINT_Y.getValue()).get(MAP_WIDTH.getValue() - 1).setFill(Color.rgb(200, 200, 200));
    }

    public List<List<GridElement>> getGrid() {
        return grid;
    }
}
