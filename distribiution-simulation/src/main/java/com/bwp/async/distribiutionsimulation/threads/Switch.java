package com.bwp.async.distribiutionsimulation.threads;

import com.bwp.async.distribiutionsimulation.util.Direction;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static com.bwp.async.distribiutionsimulation.util.MapMainValues.MAP_SWITCH_DIRECTION;

public class Switch extends Thread {

    private final AtomicBoolean isRunning;
    private final Direction[] directions = Direction.values();

    private short iter = 0;

    public Switch() {
        isRunning = new AtomicBoolean(true);
        this.setDaemon(true);
        MAP_SWITCH_DIRECTION = new AtomicReference<>(directions[iter]);
    }


    @Override
    public void run() {
        while (isRunning.get() && !isInterrupted()) {
            iter++;
            iter %= (short) directions.length;
            MAP_SWITCH_DIRECTION.set(directions[iter]);
            try {
                sleep(750);
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
            System.out.println("Switch " + this.getName() + " finish");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Błąd przy joinowaniu Switch.");
        }
    }
}
