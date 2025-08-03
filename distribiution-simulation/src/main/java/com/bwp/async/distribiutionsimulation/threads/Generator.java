package com.bwp.async.distribiutionsimulation.threads;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.bwp.async.distribiutionsimulation.util.MapMainValues.CLIENT_SLOTS;
import static com.bwp.async.distribiutionsimulation.util.MapMainValues.S_RAND;

public class Generator extends Thread {

    private final LinkedList<Person> clients;
    private final AtomicBoolean isRunning;

    public Generator(LinkedList<Person> clients) {
        isRunning = new AtomicBoolean(true);
        this.setDaemon(true);
        this.clients = clients;
    }

    @Override
    public void run() {
        while (isRunning.get() && !isInterrupted()) {
            createClients();
        }
    }

    private void createClients() {
        try {
            CLIENT_SLOTS.acquire();
            Person p = new Person(S_RAND.nextInt(50, 2000));
            synchronized (clients){
                clients.addLast(p);
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
            this.join();
            System.out.println("Generator " + this.getName() + " finish");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Błąd przy joinowaniu Generator.");
        }
    }
}
