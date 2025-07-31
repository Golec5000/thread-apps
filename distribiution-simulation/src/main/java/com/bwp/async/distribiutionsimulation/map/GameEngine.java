package com.bwp.async.distribiutionsimulation.map;

import com.bwp.async.distribiutionsimulation.threads.Person;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.LinkedList;

import static com.bwp.async.distribiutionsimulation.util.MapMainPoints.MAP_MID_POINT_Y;

public class GameEngine {

    private final AnchorPane drawingPane;
    private final MainMap map = MainMap.getInstance();
    private final LinkedList<Thread> clients = new LinkedList<>();

    private Timeline gameLoop;

    public GameEngine(AnchorPane drawingPane) {
        this.drawingPane = drawingPane;
    }

    public void start() {
        gameLoop = new Timeline(new KeyFrame(Duration.millis(50), e -> playSimulation()));
        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.play();
    }

    private void playSimulation() {
        map.renderMap(drawingPane);
        createClients();
        removeClients();
    }

    private synchronized void removeClients() {
        clients.removeIf(client -> !client.isAlive());
    }

    private synchronized void createClients() {
        if (!clients.isEmpty()) return;
        GridElement statElement = map.getGrid().get(MAP_MID_POINT_Y.getValue()).get(0);
        Person person = new Person(statElement);
        Platform.runLater(() -> statElement.setFill(person.getColor()));
        clients.addLast(person);
        clients.getLast().start();
    }

    public void stop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

}
