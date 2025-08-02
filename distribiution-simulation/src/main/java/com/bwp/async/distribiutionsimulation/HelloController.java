package com.bwp.async.distribiutionsimulation;

import com.bwp.async.distribiutionsimulation.map.GameEngine;
import com.bwp.async.distribiutionsimulation.map.GridElement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;

import java.net.URL;
import java.util.ResourceBundle;

import static com.bwp.async.distribiutionsimulation.util.MapMainValues.*;

public class HelloController implements Initializable {

    @FXML
    private Canvas drawingCanvas;

    private GameEngine gameEngine;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawingCanvas.setHeight(MAP_HEIGHT * GridElement.getDIM());
        drawingCanvas.setWidth(MAP_WIDTH * GridElement.getDIM());
        gameEngine = new GameEngine(drawingCanvas);
        gameEngine.start();
    }

    public void shutdown() {
        gameEngine.stop();
    }
}
