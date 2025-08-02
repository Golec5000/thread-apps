package com.bwp.async.distribiutionsimulation.map;

import com.bwp.async.distribiutionsimulation.threads.Generator;
import com.bwp.async.distribiutionsimulation.threads.Person;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.util.Duration;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.bwp.async.distribiutionsimulation.util.MapMainValues.MAP_MID_POINT_Y;
import static java.lang.Thread.*;
import static java.lang.Thread.sleep;

public class GameEngine {

    private final GraphicsContext gc;
    private final MainMap map = MainMap.getInstance();
    private final LinkedList<Person> clients = new LinkedList<>();
    private final Generator generator = new Generator(clients);
    private Thread clientRemover;

    private Timeline gameLoop;

    private Label threadsLabel;

    private final AtomicBoolean running = new AtomicBoolean(false);

    public GameEngine(Canvas drawingCanvas) {
        this.gc = drawingCanvas.getGraphicsContext2D();
    }

    public void start() {
        if (running.get()) return;

        running.set(true);

        // startuje główną pętlę gry (renderowanie)
        gameLoop = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            map.renderMap(gc, clients);
            Platform.runLater(() -> threadsLabel.setText("Wątki klientów: " + clients.size()));
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();

        // startuje tylko jeden wątek klienta
        generator.start();
        clientThreadRemover();
    }

    private void clientThreadRemover() {
        clientRemover = new Thread(() -> {
            while (running.get()) {
                clients.removeIf(client -> !client.isAlive());
                try {
                    sleep(new SecureRandom().nextInt(100));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("ClientCreator przerwany.");
                }
            }
        });
        clientRemover.setDaemon(true);
        clientRemover.start();
    }

    public void stop() {
        running.set(false);

        if (gameLoop != null) gameLoop.stop();

        if (generator.isAlive()) generator.interrupt();

        if (clientRemover != null && clientRemover.isAlive()) {
            try {
                clientRemover.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Błąd przy joinowaniu clientCreator.");
            }
        }

        // kończy wszystkie osoby
        synchronized (this) {
            clients.forEach(Person::interrupt);
            clients.clear();
        }
    }

    public void setThreadsLabel(Label threadsLabel) {
        this.threadsLabel = threadsLabel;
    }

    public Generator getGenerator() {
        return generator;
    }
}
