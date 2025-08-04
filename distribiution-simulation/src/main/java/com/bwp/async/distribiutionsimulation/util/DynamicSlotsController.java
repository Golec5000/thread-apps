package com.bwp.async.distribiutionsimulation.util;

import com.bwp.async.distribiutionsimulation.map.MainMap;
import com.bwp.async.distribiutionsimulation.threads.Person;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.bwp.async.distribiutionsimulation.util.MapMainValues.*;

public class DynamicSlotsController {

    private final MainMap map = MainMap.getInstance();
    private final Lock lock = new ReentrantLock();
    private final Condition generatorCondition = lock.newCondition();
    private final Condition removerCondition = lock.newCondition();

    private int maxSlots;

    public DynamicSlotsController(int maxSlots) {
        this.maxSlots = maxSlots;
    }

    public void acquireGenerator(Person person) throws InterruptedException {
        lock.lock();
        try {
            while (CLIENTS_LIST.size() >= maxSlots || map.getGrid().get(MAP_MID_POINT_Y).get(0).isOccupied()) {
                generatorCondition.await();
            }
            CLIENTS_LIST.addLast(person);
            System.out.println("Person " + person.getName() + " thread created");
            person.start();
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
            if (newMax > maxSlots) {
                generatorCondition.signal();
            }
            this.maxSlots = newMax;
        } finally {
            lock.unlock();
        }
    }
}