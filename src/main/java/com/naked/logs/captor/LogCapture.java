package com.naked.logs.captor;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LogCapture<LOG> {

    private final List<LOG> capturedLogs = new LinkedList<LOG>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public void capture(LOG log) {
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

    public List<LOG> getCapturedLogs() {
        lock.readLock().lock();
        try {
            return new LinkedList<LOG>(capturedLogs);
        } finally {
            lock.readLock().unlock();
        }
    }

}