package com.naked.logs.logback.captor;
import java.util.List;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import com.naked.logs.captor.LogCapture;

public class LogbackCapturingAppender extends AppenderBase<ILoggingEvent> {

    private final LogCapture<ILoggingEvent> logCapture;

    public LogbackCapturingAppender(LogCapture<ILoggingEvent> logCapture) {
        this.logCapture = logCapture;
    }

    @Override
    protected void append(ILoggingEvent event) {
        logCapture.capture(event);
    }

    public void reset() {
        logCapture.reset();
    }

    public List<ILoggingEvent> getCapturedLogs() {
        return logCapture.getCapturedLogs();
    }

}