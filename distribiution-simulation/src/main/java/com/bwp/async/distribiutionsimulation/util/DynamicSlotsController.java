package com.bwp.async.distribiutionsimulation.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.bwp.async.distribiutionsimulation.util.MapMainValues.*;

public class DynamicSlotsController {
    private final Lock lock = new ReentrantLock();
    private final Condition generatorCondition = lock.newCondition();
    private final Condition removerCondition = lock.newCondition();

    private final AtomicInteger maxSlots;

    public DynamicSlotsController(int maxSlots) {
        this.maxSlots = new AtomicInteger(maxSlots);
    }

    public void acquireGenerator() throws InterruptedException {
        lock.lock();
        try {
            while (CLIENTS_LIST.size() >= maxSlots.get()) generatorCondition.await();
        } finally {
            lock.unlock();
        }
    }

    public void acquireRemover() throws InterruptedException {
        lock.lock();
        try {
           removerCondition.await();
        } finally {
            lock.unlock();
        }
    }

    public void notifyGenerator() {
        lock.lock();
        try {
            generatorCondition.signal();
        } finally {
            lock.unlock();
        }
    }


    public void notifyRemover() {
        lock.lock();
        try {
            removerCondition.signal();
        } finally {
            lock.unlock();
        }
    }


    public void setMaxSlots(int newMax) {
        lock.lock();
        try {
            if (newMax > maxSlots.get()) {
                generatorCondition.signal();
            }
            this.maxSlots.set(newMax);
        } finally {
            lock.unlock();
        }
    }
}