package com.bwp.async.distribiutionsimulation;

import com.bwp.async.distribiutionsimulation.map.GameEngine;
import com.bwp.async.distribiutionsimulation.map.GridElement;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

import static com.bwp.async.distribiutionsimulation.util.MapMainValues.*;

public class HelloController implements Initializable {

    @FXML
    private Canvas drawingCanvas;

    @FXML
    private Label threadsLabel;

    @FXML
    private Label limitThreadLabel;

    @FXML
    private Label switchStatusLabel;

    @FXML
    private Label generatorStatusLabel;

    @FXML
    private Label cleanupStatusLabel;

    @FXML
    private Slider maxClientsInput;


    private GameEngine gameEngine;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawingCanvas.setHeight(MAP_HEIGHT * GridElement.getDIM());
        drawingCanvas.setWidth(MAP_WIDTH * GridElement.getDIM());

        gameEngine = new GameEngine(drawingCanvas);
        gameEngine.setThreadsLabel(threadsLabel);
        gameEngine.setSwitchStatusLabel(switchStatusLabel);
        gameEngine.setGeneratorStatusLabel(generatorStatusLabel);
        gameEngine.setCleanupStatusLabel(cleanupStatusLabel);
        gameEngine.start();

        maxClientsInput.valueProperty().addListener((observableValue, number, t1) -> {
            int limit = (int) maxClientsInput.getValue();
            CLIENT_SLOTS.setMaxSlots(limit);
            Platform.runLater(() -> limitThreadLabel.setText("Thread limit: " + limit));
        });
    }

    public void shutdown() {
        gameEngine.stop();
    }
}
