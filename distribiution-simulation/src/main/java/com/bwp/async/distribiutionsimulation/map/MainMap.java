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
        List<List<GridElement>> grid = new ArrayList<>(MAP_HEIGHT.getVaule());
        for (int x = 0; x < MAP_HEIGHT.getVaule(); x++) {
            List<GridElement> row = new ArrayList<>(MAP_WIDTH.getVaule());
            for (int y = 0; y < MAP_WIDTH.getVaule(); y++) {
                row.add(new GridElement(y * GridElement.getDIM(), x * GridElement.getDIM(), Color.CORNSILK, x, y));
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
        grid.get(MAP_MID_POINT_Y.getVaule())
                .get(MAP_MID_POINT_X.getVaule())
                .setFill(Color.rgb(200, 200, 200));

        drawingPane.getChildren().clear();
        grid.forEach(row -> drawingPane.getChildren().addAll(row));
    }

    private void renderUpLane() {
        for (int i = MAP_MID_POINT_Y.getVaule(); i >= 0; i--)
            grid.get(i).get(MAP_MID_POINT_X.getVaule()).setFill(Color.RED);
        for (int i = MAP_MID_POINT_X.getVaule(); i < MAP_WIDTH.getVaule(); i++)
            grid.get(0).get(i).setFill(Color.RED);
        grid.get(0).get(MAP_WIDTH.getVaule() - 1).setFill(Color.rgb(200, 200, 200));
    }

    private void renderBottomLane() {
        for (int i = MAP_MID_POINT_Y.getVaule(); i < MAP_HEIGHT.getVaule(); i++)
            grid.get(i).get(MAP_MID_POINT_X.getVaule()).setFill(Color.RED);
        for (int i = MAP_MID_POINT_X.getVaule(); i < MAP_WIDTH.getVaule(); i++)
            grid.get(MAP_HEIGHT.getVaule() - 1).get(i).setFill(Color.RED);
        grid.get(MAP_HEIGHT.getVaule() - 1).get(MAP_WIDTH.getVaule() - 1).setFill(Color.rgb(200, 200, 200));
    }

    private void renderMidLane() {
        grid.get(MAP_MID_POINT_Y.getVaule()).forEach(element -> element.setFill(Color.RED));
        grid.get(MAP_MID_POINT_Y.getVaule()).get(MAP_WIDTH.getVaule() - 1).setFill(Color.rgb(200, 200, 200));
    }

    public List<List<GridElement>> getGrid() {
        return grid;
    }
}
