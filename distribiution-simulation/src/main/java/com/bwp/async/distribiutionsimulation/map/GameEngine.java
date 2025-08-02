package com.bwp.async.distribiutionsimulation.map;

import com.bwp.async.distribiutionsimulation.threads.Generator;
import com.bwp.async.distribiutionsimulation.threads.Person;
import com.bwp.async.distribiutionsimulation.threads.Remover;
import com.bwp.async.distribiutionsimulation.threads.Switch;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.bwp.async.distribiutionsimulation.util.MapMainValues.MAP_SWITCH_DIRECTION;

public class GameEngine {

    private final GraphicsContext gc;
    private final MainMap map = MainMap.getInstance();
    private final LinkedList<Person> clients = new LinkedList<>();
    private final Generator generator = new Generator(clients);
    private final Remover remover = new Remover(clients);
    private final Switch directionSwitch = new Switch();
    private final AtomicBoolean running = new AtomicBoolean(false);

    private Timeline gameLoop;
    private Label threadsLabel;
    private Label switchStatusLabel;

    public GameEngine(Canvas drawingCanvas) {
        this.gc = drawingCanvas.getGraphicsContext2D();
    }

    public void start() {
        if (running.get()) return;

        running.set(true);

        // startuje główną pętlę gry (renderowanie)
        gameLoop = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            map.renderMap(gc, clients);
            Platform.runLater(() -> threadsLabel.setText("Number of clients: " + clients.size()));
            Platform.runLater(() -> switchStatusLabel.setText("Switch direction: " + MAP_SWITCH_DIRECTION.get()));
        }));

        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();

        generator.start();
        remover.start();
        directionSwitch.start();
    }

    public void stop() {
        running.set(false);

        if (gameLoop != null) gameLoop.stop();
        if (generator.isAlive()) generator.interrupt();
        if (remover.isAlive()) remover.interrupt();
        if (directionSwitch.isAlive()) directionSwitch.interrupt();

        // kończy wszystkie osoby
        synchronized (this) {
            clients.forEach(Person::interrupt);
            clients.clear();
        }
    }

    public void setThreadsLabel(Label threadsLabel) {
        this.threadsLabel = threadsLabel;
    }

    public void setSwitchStatusLabel(Label switchStatusLabel) {
        this.switchStatusLabel = switchStatusLabel;
    }

    public Generator getGenerator() {
        return generator;
    }
}
