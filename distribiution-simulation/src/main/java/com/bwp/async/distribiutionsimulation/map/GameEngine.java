package com.bwp.async.distribiutionsimulation.map;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class GameEngine implements Runnable {

    private static final int ROW_NUM = 30;
    private static final int COL_NUM = 40;

    private final List<List<Rectangle>> grid = new ArrayList<>(ROW_NUM);
    private final AnchorPane drawingPane;

    public GameEngine(AnchorPane drawingPane) {
        this.drawingPane = drawingPane;
    }

    @Override
    public void run() {
        mapInit();
        renderMap();
    }

    private void mapInit() {
        for (int x = 0; x < ROW_NUM; x++) {
            List<Rectangle> row = new ArrayList<>(COL_NUM);
            for (int y = 0; y < COL_NUM; y++) {
                Rectangle rect = new GridElement(y * GridElement.getDIM(), x * GridElement.getDIM(), Color.CORNSILK);
                row.add(rect);
            }
            grid.add(row);
        }
    }

    private void renderMap() {
        drawingPane.getChildren().clear();
        for (List<Rectangle> row : grid) {
            drawingPane.getChildren().addAll(row);
        }
    }
}
