package com.bwp.async.distribiutionsimulation.threads;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.bwp.async.distribiutionsimulation.util.MapMainValues.REMOVER_LOCK;

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
            try {
                synchronized (REMOVER_LOCK){
                    REMOVER_LOCK.wait();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Remover interrupted");
            }

            synchronized (clients) {
                clients.removeIf(client -> !client.isAlive());
            }
        }
    }

    public void notifyToRemove() {
        synchronized (REMOVER_LOCK) {
            REMOVER_LOCK.notify();
        }
    }


    @Override
    public void interrupt() {
        try {
            isRunning.set(false);
            notifyToRemove();
            this.join();
            System.out.println("Remover " + this.getName() + " finish");
        } catch (InterruptedException e) {
            currentThread().interrupt();
            System.err.println("Błąd przy joinowaniu Remover.");
        }
    }
}
