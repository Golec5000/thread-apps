package com.bwp.async.distribiutionsimulation.threads;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.bwp.async.distribiutionsimulation.util.MapMainValues.CLIENTS_LIST;
import static com.bwp.async.distribiutionsimulation.util.MapMainValues.CLIENT_SLOTS;

public class Remover extends Thread {

    private final AtomicBoolean isRunning;

    public Remover() {
        this.isRunning = new AtomicBoolean(true);
        this.setDaemon(true);
    }

    @Override
    public void run() {
        while (isRunning.get() && !isInterrupted()) {
            try {
                CLIENT_SLOTS.acquireRemover();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Remover interrupted");
            }

            synchronized (CLIENTS_LIST) {
                if (CLIENTS_LIST.removeIf(client -> !client.isAlive())) {
                    CLIENT_SLOTS.notifyGenerator();
                }
            }
        }
    }



    public void stopThread() {
        try {
            isRunning.set(false);
            CLIENT_SLOTS.notifyRemover();
            this.join();
            System.out.println("Remover " + this.getName() + " finish");
        } catch (InterruptedException e) {
            currentThread().interrupt();
            System.err.println("Błąd przy joinowaniu Remover.");
        }
    }
}
