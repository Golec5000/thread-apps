package com.bwp.async.distribiutionsimulation.map;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {

    private static final int MAP_HEIGHT = 31;
    private static final int MAP_WIDTH = 40;
    private static final int MAP_MID_POINT_Y = 15;
    private static final int MAP_MID_POINT_X = 30;

    private final List<List<GridElement>> grid = new ArrayList<>(MAP_HEIGHT);
    private final AnchorPane drawingPane;

    private Timeline gameLoop;

    public GameEngine(AnchorPane drawingPane) {
        this.drawingPane = drawingPane;
    }

    public void start() {
        mapInit();
        gameLoop = new Timeline(new KeyFrame(Duration.millis(200), e -> renderMap()));
        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.play();
    }

    public void stop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

    private void mapInit() {
        for (int x = 0; x < MAP_HEIGHT; x++) {
            List<GridElement> row = new ArrayList<>(MAP_WIDTH);
            for (int y = 0; y < MAP_WIDTH; y++) {
                row.add(new GridElement(y * GridElement.getDIM(), x * GridElement.getDIM(), Color.CORNSILK));
            }
            grid.add(row);
        }
    }

    private void renderMap() {
        //Basic map part
        renderUpLane();
        renderMidLane();
        renderBotomLane();
        grid.get(MAP_MID_POINT_Y).get(MAP_MID_POINT_X).setFill(Color.rgb(200, 200, 200));

        drawingPane.getChildren().clear();
        grid.forEach(row -> drawingPane.getChildren().addAll(row));
    }

    private void renderBotomLane() {
        for (int i = MAP_MID_POINT_Y; i >= 0; i--)
            grid.get(i).get(MAP_MID_POINT_X).setFill(Color.RED);
        for (int i = MAP_MID_POINT_X; i < MAP_WIDTH; i++)
            grid.get(0).get(i).setFill(Color.RED);
        grid.get(0).get(MAP_WIDTH - 1).setFill(Color.rgb(200, 200, 200));
    }

    private void renderUpLane() {
        for (int i = MAP_MID_POINT_Y; i < MAP_HEIGHT; i++)
            grid.get(i).get(MAP_MID_POINT_X).setFill(Color.RED);
        for (int i = MAP_MID_POINT_X; i < MAP_WIDTH; i++)
            grid.get(MAP_HEIGHT - 1).get(i).setFill(Color.RED);
        grid.get(MAP_HEIGHT - 1).get(MAP_WIDTH - 1).setFill(Color.rgb(200, 200, 200));
    }

    private void renderMidLane() {
        grid.get(MAP_MID_POINT_Y).forEach(element -> element.setFill(Color.RED));
        grid.get(MAP_MID_POINT_Y).get(MAP_WIDTH - 1).setFill(Color.rgb(200, 200, 200));
    }
}
