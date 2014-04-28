package com.naked.logs.captor;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LogCapture<T> {

    private final List<T> capturedLogs = new LinkedList<T>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public void capture(T log) {
        lock.writeLock().lock();
        try {
            capturedLogs.add(log);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void reset() {
        capturedLogs.clear();
    }

    public List<T> getCapturedLogs() {
        lock.readLock().lock();
        try {
            return new LinkedList<T>(capturedLogs);
        } finally {
            lock.readLock().unlock();
        }
    }

}