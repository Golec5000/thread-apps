package com.bwp.async.distribiutionsimulation;

import com.bwp.async.distribiutionsimulation.map.GameEngine;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private AnchorPane drawingPane;

    private GameEngine gameEngine;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameEngine = new GameEngine(drawingPane);
        gameEngine.start();
    }
}
