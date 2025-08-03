package com.bwp.async.distribiutionsimulation.util;

public class DynamicSlotsController {
    private int maxSlots;
    private int currentUsed = 0;

    public DynamicSlotsController(int maxSlots) {
        this.maxSlots = maxSlots;
    }

    public synchronized void acquire() throws InterruptedException {
        while (currentUsed >= maxSlots) {
            wait();
        }
        currentUsed++;
    }

    public synchronized void release() {
        currentUsed--;
        if (currentUsed < 0) currentUsed = 0;
        notify();
    }

    public synchronized void setMaxSlots(int newMax) {
        this.maxSlots = newMax;
        notify();
    }
}