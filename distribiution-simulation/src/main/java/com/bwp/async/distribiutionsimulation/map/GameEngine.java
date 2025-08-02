package com.bwp.async.distribiutionsimulation.map;

import com.bwp.async.distribiutionsimulation.threads.Person;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
    private Thread clientCreator;
    private Thread clientRemover;

    private Timeline gameLoop;

    private final AtomicBoolean running = new AtomicBoolean(false);

    public GameEngine(Canvas drawingCanvas) {
        this.gc = drawingCanvas.getGraphicsContext2D();
    }

    public void start() {
        if (running.get()) return;

        running.set(true);

        // startuje główną pętlę gry (renderowanie)
        gameLoop = new Timeline(new KeyFrame(Duration.millis(16), e -> map.renderMap(gc, clients)));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();

        // startuje tylko jeden wątek klienta
        clientThreadGenerator();
        clientThreadRemover();
    }

    private void clientThreadGenerator() {
        clientCreator = new Thread(() -> {
            while (running.get()) {
                createClients();
                try {
                    sleep(new SecureRandom().nextInt(200, 2000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("ClientCreator przerwany.");
                }
            }
        });
        clientCreator.setDaemon(true);
        clientCreator.start();
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

    private synchronized void createClients() {
        if (clients.size() > 10) return;
        GridElement statElement = map.getGrid().get(MAP_MID_POINT_Y).get(0);
        Person person = new Person(statElement);
        statElement.setColor(person.getColor());
        clients.addLast(person);
        clients.getLast().start();
    }

    public void stop() {
        running.set(false);

        if (gameLoop != null) {
            gameLoop.stop();
        }

        if (clientCreator != null && clientCreator.isAlive()) {
            try {
                clientCreator.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Błąd przy joinowaniu clientCreator.");
            }
        }

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
            for (Person person : clients) {
                person.interrupt();
            }
        }

        clients.clear();
    }
}
