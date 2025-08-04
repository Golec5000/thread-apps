package com.bwp.async.distribiutionsimulation.map;

import com.bwp.async.distribiutionsimulation.threads.Person;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.bwp.async.distribiutionsimulation.util.MapMainValues.*;

public class GameEngine {

    private final GraphicsContext gc;
    private final MainMap map = MainMap.getInstance();
    private final AtomicBoolean running = new AtomicBoolean(false);

    private Timeline gameLoop;
    private Label threadsLabel;
    private Label switchStatusLabel;
    private Label generatorStatusLabel;
    private Label cleanupStatusLabel;

    public GameEngine(Canvas drawingCanvas) {
        this.gc = drawingCanvas.getGraphicsContext2D();
    }

    public void start() {
        if (running.get()) return;

        running.set(true);

        // startuje główną pętlę gry (renderowanie)
        gameLoop = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            map.renderMap(gc, CLIENTS_LIST);
            Platform.runLater(() -> threadsLabel.setText("Number of clients: " + CLIENTS_LIST.size()));
            Platform.runLater(() -> switchStatusLabel.setText("Switch direction: " + MAP_SWITCH_DIRECTION.get()));
            Platform.runLater(() -> generatorStatusLabel.setText("Generator: " + GENERATOR.getState().name()));
            Platform.runLater(() -> cleanupStatusLabel.setText("Remover: " + REMOVER.getState().name()));
        }));

        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();

        GENERATOR.start();
        REMOVER.start();
        DIRECTION_SWITCH.start();
    }

    public void stop() {
        running.set(false);

        CLIENT_SLOTS.notifyRemover();
        CLIENT_SLOTS.notifyGenerator();

        if (gameLoop != null) gameLoop.stop();
        if (GENERATOR.isAlive()) GENERATOR.stopThread();
        if (REMOVER.isAlive()) REMOVER.stopThread();
        if (DIRECTION_SWITCH.isAlive()) DIRECTION_SWITCH.stopThread();

        // kończy wszystkie osoby
        synchronized (this) {
            CLIENTS_LIST.forEach(Person::stopThread);
            CLIENTS_LIST.clear();
        }
    }

    public void setThreadsLabel(Label threadsLabel) {
        this.threadsLabel = threadsLabel;
    }

    public void setSwitchStatusLabel(Label switchStatusLabel) {
        this.switchStatusLabel = switchStatusLabel;
    }

    public void setGeneratorStatusLabel(Label generatorStatusLabel) {
        this.generatorStatusLabel = generatorStatusLabel;
    }

    public void setCleanupStatusLabel(Label cleanupStatusLabel) {
        this.cleanupStatusLabel = cleanupStatusLabel;
    }
}
