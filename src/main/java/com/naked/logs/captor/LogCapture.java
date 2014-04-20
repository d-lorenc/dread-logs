package com.naked.logs.captor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LogCapture<T> {

    private final List<T> capturedLogs = Collections.synchronizedList(new LinkedList<T>());

    public void capture(T log) {
        capturedLogs.add(log);
    }

    public void reset() {
        capturedLogs.clear();
    }

    public List<T> getCapturedLogs() {
        return new LinkedList<T>(capturedLogs);
    }

}