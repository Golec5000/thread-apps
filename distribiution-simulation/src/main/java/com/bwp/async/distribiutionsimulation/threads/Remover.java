package com.bwp.async.distribiutionsimulation.threads;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Remover extends Thread {

    private final LinkedList<Person> clients;
    private final AtomicBoolean isRunning;

    public Remover(LinkedList<Person> clients) {
        this.isRunning = new AtomicBoolean(true);
        this.setDaemon(true);
        this.clients = clients;
    }

    @Override
    public void run() {
        while (isRunning.get() && !isInterrupted()) {
            synchronized (clients) {
                clients.removeIf(client -> !client.isAlive());
            }
            try {
                sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println(this.getName() + " przerwany");
            }
        }
    }

    @Override
    public void interrupt() {
        try {
            isRunning.set(false);
            this.join();
            System.out.println("Remover " + this.getName() + " finish");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Błąd przy joinowaniu Remover.");
        }
    }
}
