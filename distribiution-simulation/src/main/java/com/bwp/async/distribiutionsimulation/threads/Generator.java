package com.bwp.async.distribiutionsimulation.threads;

import com.bwp.async.distribiutionsimulation.map.MainMap;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.bwp.async.distribiutionsimulation.util.MapMainValues.MAP_MID_POINT_Y;

public class Generator extends Thread {

    private final LinkedList<Person> clients;
    private final MainMap map = MainMap.getInstance();
    private final AtomicBoolean isRunning;

    private int limit;

    public Generator(LinkedList<Person> clients){
        isRunning = new AtomicBoolean(true);
        this.setDaemon(true);
        this.clients = clients;
    }

    @Override
    public void run() {
        while (isRunning.get() && !isInterrupted()){
            createClients();
            try {
                sleep(new SecureRandom().nextInt(200, 1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println(this.getName() + " przerwany");
            }
        }
    }

    private synchronized void createClients() {
        if (clients.size() >= limit) return;
        clients.addLast(new Person(map.getGrid().get(MAP_MID_POINT_Y).get(0)));
        clients.getLast().start();
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public void interrupt(){
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
