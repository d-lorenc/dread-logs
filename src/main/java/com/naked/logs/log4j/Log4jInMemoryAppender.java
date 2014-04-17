package com.naked.logs.log4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

public class Log4jInMemoryAppender extends AppenderSkeleton {
	
    private List<LoggingEvent> events = Collections.synchronizedList(new LinkedList<LoggingEvent>());
    private boolean stdOutLoggingEnabled;

    public Log4jInMemoryAppender(Layout layout) {
    	this.layout = layout;
	}

    public void enableStdOutLogging() {
        this.stdOutLoggingEnabled = true;
    }

    @Override
    protected void append(LoggingEvent event) {
        event.getMDCCopy();
        events.add(event);
        logToStdOut(event);
    }

	private void logToStdOut(LoggingEvent event) {
		if (stdOutLoggingEnabled) {
            System.out.println(layout.format(event));
        }
	}
	
	public void close() {
	}

	public boolean requiresLayout() {
		return false;
	}
	
    public void reset() {
        events.clear();
    }
    
    List<LoggingEvent> getCopyOfEvents() {
        return new ArrayList<LoggingEvent>(events);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (LoggingEvent event : getCopyOfEvents()) {
            builder.append(layout.format(event));
        }
        return builder.toString();
    }
    
}
