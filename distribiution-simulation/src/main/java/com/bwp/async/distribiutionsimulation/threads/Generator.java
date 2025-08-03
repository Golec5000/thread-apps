package com.bwp.async.distribiutionsimulation.threads;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.bwp.async.distribiutionsimulation.util.MapMainValues.*;

public class Generator extends Thread {

    private final AtomicBoolean isRunning;

    public Generator() {
        isRunning = new AtomicBoolean(true);
        this.setDaemon(true);
    }

    @Override
    public void run() {
        while (isRunning.get() && !isInterrupted()) {
            createClients();
        }
    }

    private void createClients() {
        try {
            CLIENT_SLOTS.acquireGenerator();
            Person p = new Person(S_RAND.nextInt(50, 750));
            synchronized (CLIENTS_LIST){
                CLIENTS_LIST.addLast(p);
            }
            p.start();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Generator interrupted");
        }
    }

    @Override
    public void interrupt() {
        try {
            isRunning.set(false);
            CLIENT_SLOTS.notifyGenerator();
            this.join();
            System.out.println("Generator " + this.getName() + " finish");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Błąd przy joinowaniu Generator.");
        }
    }
}
